package com.weapon.simulator

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF08090D)
private val Panel = Color(0xFF141722)
private val Red = Color(0xFFFF334F)
private val Blue = Color(0xFF37B9FF)
private val Purple = Color(0xFFB65CFF)

internal enum class Section(val title: String) {
    Weapons("Weapons"), Energy("Energy Tools"), Barber("Barber Tools")
}

private val Section.accent: Color
    get() = when (this) {
        Section.Weapons -> Red
        Section.Energy -> Blue
        Section.Barber -> Purple
    }

private data class SimItem(val name: String, val section: Section, val category: String)

private val items = listOf(
    "AK-47" to "Assault Rifles", "M4A1" to "Assault Rifles", "Desert Eagle" to "Pistols",
    "AWM Sniper" to "Snipers", "Shotgun" to "Shotguns", "Minigun" to "Special",
    "Glock 19" to "Pistols", "Beretta 92" to "Pistols", "Revolver" to "Pistols",
    "Uzi" to "SMG", "MP5" to "SMG", "P90" to "SMG", "Vector SMG" to "SMG",
    "SCAR-L" to "Assault Rifles", "AUG A3" to "Assault Rifles", "FAMAS" to "Assault Rifles",
    "G36" to "Assault Rifles", "M16" to "Assault Rifles", "Tactical Pistol" to "Pistols",
    "USP Tactical" to "Pistols", "SPAS-12" to "Shotguns", "Pump Shotgun" to "Shotguns",
    "Barrett M82" to "Snipers", "Dragunov SVD" to "Snipers", "Thompson" to "SMG",
    "Kriss Vector" to "SMG", "Laser Gun" to "Special", "Plasma Rifle" to "Special",
    "Energy Cannon" to "Special", "Neon Rifle" to "Special"
).map { SimItem(it.first, Section.Weapons, it.second) } + listOf(
    "Thunder Blade", "Golden Scepter", "Void Staff", "Ice Scepter", "Crimson Mace", "Lightning Orb",
    "Electric Gauntlet", "Storm Spear", "Tesla Rod", "Lightning Whip", "Shadow Claw", "Holy Hammer",
    "Crystal Staff", "Plasma Staff", "Dragon Staff", "Nature Lightning", "Chaos Staff", "Electric Fan",
    "Cosmic Staff", "Dual Lightning Blades", "Lightning Blaster", "Energy Vortex", "Lightning Lance", "Thunder Tornado"
).map { SimItem(it, Section.Energy, "Electric") } + listOf(
    "Hair Clipper", "Professional Clipper", "Classic Clipper", "Beard Trimmer", "Hair Trimmer", "Mini Trimmer",
    "Vintage Clipper", "Power Clipper", "Metal Clipper", "Premium Clipper", "Cordless Clipper", "Barber Pro X",
    "Titanium Clipper", "Gold Master", "Chrome Master", "Carbon Fiber Clipper", "Neon Clipper", "Cyber Trimmer",
    "Turbo Clipper", "Ultimate Clipper", "Scissors", "Electric Shaver", "Hair Dryer", "Spray Bottle",
    "Shaving Foam", "Comb", "Barber Brush"
).map { SimItem(it, Section.Barber, if (it == "Scissors") "Scissors" else "Clippers") }

/**
 * SoundPool controller for short offline SFX.
 * Every catalog item receives a local raw resource. If the catalog has fewer
 * dedicated files than items, the closest category pool is reused round-robin.
 */
private class ItemSoundController(context: Context, catalog: List<SimItem>) {
    private val soundPool: SoundPool
    private val sampleByItem = mutableMapOf<String, Int>()
    private val sampleByResource = mutableMapOf<Int, Int>()
    private val loadedSamples = mutableSetOf<Int>()
    private var activeStreamId = 0

    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .setMaxStreams(1)
            .build()
        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) loadedSamples += sampleId
        }

        val weaponSounds = listOf(
            R.raw.weapons_mixkit_2782, R.raw.weapons_mixkit_1660, R.raw.weapons_mixkit_1662,
            R.raw.weapons_mixkit_2804, R.raw.weapons_mixkit_2779, R.raw.weapons_mixkit_2780,
            R.raw.weapons_mixkit_1693, R.raw.weapons_mixkit_1666, R.raw.weapons_mixkit_1670,
            R.raw.weapons_mixkit_2777, R.raw.weapons_mixkit_2784, R.raw.weapons_mixkit_1674,
            R.raw.weapons_mixkit_2778, R.raw.weapons_mixkit_1681, R.raw.weapons_mixkit_1668,
            R.raw.weapons_mixkit_1679, R.raw.weapons_mixkit_2783, R.raw.weapons_mixkit_1665,
            R.raw.weapons_mixkit_1678, R.raw.weapons_mixkit_1659, R.raw.weapons_mixkit_1664,
            R.raw.weapons_mixkit_1680, R.raw.weapons_mixkit_223
        )
        val energySounds = listOf(
            R.raw.energy_mixkit_2595, R.raw.energy_mixkit_2594, R.raw.energy_mixkit_2596,
            R.raw.energy_mixkit_2599, R.raw.energy_mixkit_2365, R.raw.energy_mixkit_2603,
            R.raw.energy_mixkit_1377, R.raw.energy_mixkit_3201, R.raw.energy_mixkit_1455,
            R.raw.energy_mixkit_2614, R.raw.energy_mixkit_1811, R.raw.energy_mixkit_1808,
            R.raw.energy_mixkit_2969, R.raw.energy_mixkit_2597, R.raw.energy_mixkit_2967,
            R.raw.energy_mixkit_1453, R.raw.energy_mixkit_1054, R.raw.energy_mixkit_2340,
            R.raw.energy_mixkit_2966, R.raw.energy_mixkit_2646, R.raw.energy_mixkit_2968,
            R.raw.energy_mixkit_1814, R.raw.energy_mixkit_3032, R.raw.energy_mixkit_2961
        )
        val barberSounds = listOf(
            R.raw.barber_mixkit_2378, R.raw.barber_mixkit_1894, R.raw.barber_mixkit_1895
        )
        val resourcesBySection = mapOf(
            Section.Weapons to weaponSounds,
            Section.Energy to energySounds,
            Section.Barber to barberSounds
        )
        resourcesBySection.values.flatten().forEach { resourceId ->
            if (resourceId !in sampleByResource) {
                sampleByResource[resourceId] = soundPool.load(context, resourceId, 1)
            }
        }
        Section.entries.forEach { section ->
            val sectionItems = catalog.filter { it.section == section }
            val resources = resourcesBySection.getValue(section)
            sectionItems.forEachIndexed { index, item ->
                val resourceId = resources[index % resources.size]
                sampleByItem[item.name] = resourceId
            }
        }
    }

    fun play(item: SimItem): Int {
        stop()
        val resourceId = sampleByItem[item.name] ?: return 0
        val sampleId = sampleByResource[resourceId] ?: return 0
        if (sampleId == 0 || sampleId !in loadedSamples) return 0
        activeStreamId = soundPool.play(sampleId, 1f, 1f, 1, -1, 1f)
        return activeStreamId
    }

    fun stop(streamId: Int = activeStreamId) {
        if (streamId != 0) soundPool.stop(streamId)
        if (streamId == activeStreamId) activeStreamId = 0
    }

    fun release() {
        stop()
        soundPool.release()
        loadedSamples.clear()
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WeaponSimulatorApp() }
    }
}

@Composable
private fun WeaponSimulatorApp() {
    val context = LocalContext.current
    val soundController = remember(context) { ItemSoundController(context, items) }
    DisposableEffect(soundController) { onDispose { soundController.release() } }
    var selectedSection by remember { mutableStateOf<Section?>(null) }
    var showSettings by remember { mutableStateOf(false) }
    var showFavorites by remember { mutableStateOf(false) }
    var favorites by remember { mutableStateOf(setOf<String>()) }
    var safeMode by remember { mutableStateOf(false) }
    var flashStrength by remember { mutableStateOf(0.65f) }

    MaterialTheme(colorScheme = androidx.compose.material3.darkColorScheme(background = Background, surface = Panel, primary = Red)) {
        Surface(modifier = Modifier.fillMaxSize(), color = Background) {
            when {
                showSettings -> SettingsScreen(safeMode, { safeMode = it }, flashStrength, { flashStrength = it }, { showSettings = false })
                showFavorites -> ItemGridScreen("Favorites", null, items.filter { it.name in favorites }, favorites, { favorites = toggle(favorites, it) }, soundController) { showFavorites = false }
                selectedSection != null -> ItemGridScreen(selectedSection!!.title, selectedSection, items.filter { it.section == selectedSection }, favorites, { favorites = toggle(favorites, it) }, soundController) { selectedSection = null }
                else -> HomeScreen(onSection = { selectedSection = it }, onFavorites = { showFavorites = true }, onSettings = { showSettings = true })
            }
        }
    }
}

private fun toggle(set: Set<String>, name: String) = if (name in set) set - name else set + name

@Composable
private fun HomeScreen(onSection: (Section) -> Unit, onFavorites: () -> Unit, onSettings: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column { Text("WEAPON", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black); Text("SIMULATOR", color = Red, fontSize = 14.sp, letterSpacing = 4.sp) }
            Row { IconButton(onClick = onFavorites) { Icon(Icons.Default.Favorite, "Favorites", tint = Red) }; IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, "Settings", tint = Color.White) } }
        }
        Spacer(Modifier.height(22.dp)); Text("Choose your experience", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(14.dp))
        HomeCard("Weapons", "Realistic weapon simulator", Red) { onSection(Section.Weapons) }
        HomeCard("Energy Tools", "Electric and fantasy effects", Blue) { onSection(Section.Energy) }
        HomeCard("Barber Tools", "Clippers and salon tools", Purple) { onSection(Section.Barber) }
        Spacer(Modifier.weight(1f)); Text("Favorites  •  Mini Games  •  Gallery  •  Premium", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun HomeCard(title: String, subtitle: String, accent: Color, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(vertical = 7.dp).clickable(onClick = onClick), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(56.dp).background(accent.copy(alpha = .18f), RoundedCornerShape(16.dp)))
            Spacer(Modifier.width(16.dp)); Column { Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold); Text(subtitle, color = Color.Gray, fontSize = 13.sp) }
        }
    }
}

@Composable
private fun ItemGridScreen(
    title: String,
    section: Section?,
    data: List<SimItem>,
    favorites: Set<String>,
    onFavorite: (String) -> Unit,
    soundController: ItemSoundController,
    onBack: () -> Unit
) {
    var activeItem by remember { mutableStateOf<String?>(null) }
    DisposableEffect(Unit) { onDispose { soundController.stop() } }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { OutlinedButton(onClick = onBack) { Text("Back") }; Spacer(Modifier.width(12.dp)); Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp), modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(data) { item ->
                val accent = section?.accent ?: item.section.accent
                val pressed = activeItem == item.name
                Card(modifier = Modifier.pointerInput(item.name) {
                    detectTapGestures(onPress = {
                        activeItem = item.name
                        val streamId = soundController.play(item)
                        try { awaitRelease() } finally {
                            soundController.stop(streamId)
                            activeItem = null
                        }
                    })
                }, colors = CardDefaults.cardColors(containerColor = if (pressed) accent.copy(alpha = .28f) else Panel), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Box(Modifier.fillMaxWidth().height(105.dp).background(Brush.radialGradient(listOf(accent.copy(alpha = .40f), Color.Transparent)), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) { Icon(Icons.Default.VolumeUp, null, tint = accent, modifier = Modifier.size(40.dp)) }
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { Text(item.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f)); IconButton(onClick = { onFavorite(item.name) }) { Icon(if (item.name in favorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorite", tint = if (item.name in favorites) Red else Color.Gray) } }
                        Text(item.category, color = accent, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(safeMode: Boolean, onSafeMode: (Boolean) -> Unit, strength: Float, onStrength: (Float) -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        OutlinedButton(onClick = onBack) { Text("Back") }; Spacer(Modifier.height(20.dp)); Text("Settings", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(20.dp))
        Text("Flash intensity", color = Color.White); Slider(value = strength, onValueChange = onStrength); Text("Safe mode reduces rapid screen flashes", color = Color.Gray)
        Row(Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text("Safe effects mode", color = Color.White); androidx.compose.material3.Switch(checked = safeMode, onCheckedChange = onSafeMode) }
        Spacer(Modifier.height(30.dp)); Text("Language: Arabic / English", color = Color.White); Text("SoundPool is active for offline press-and-hold effects.", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
    }
}
