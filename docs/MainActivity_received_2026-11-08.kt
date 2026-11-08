package com.weapon.simulator

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF05070D)
private val Panel = Color(0xFF111725)
private val PanelRaised = Color(0xFF182236)
private val Red = Color(0xFFFF354D)
private val Blue = Color(0xFF16CFFF)
private val Purple = Color(0xFFC22CFF)
private val Gold = Color(0xFFFFC928)
private val Muted = Color(0xFF9AA8BE)

internal enum class Section(val title: String, val subtitle: String, val tint: Color) {
    Weapons("Weapons", "30 REALISTIC WEAPONS", Red),
    Energy("Energy Tools", "24 ELECTRIC EFFECTS", Blue),
    Barber("Barber Tools", "27 PROFESSIONAL TOOLS", Purple)
}

private enum class Destination(val label: String) {
    Home("Home"), Weapons("Weapons"), Energy("Energy"), Barber("Barber"), Settings("Settings"),
    Favorites("Favorites"), MiniGames("Mini Games"), Gallery("Gallery"), Premium("Premium")
}

private data class SimItem(val name: String, val section: Section, val category: String) {
    val drawableName: String = "item_" + name.lowercase()
        .replace(Regex("[^a-z0-9]+"), "_")
        .trim('_')
}

private val catalog: List<SimItem> = listOf(
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
).map { SimItem(it, Section.Energy, energyCategory(it)) } + listOf(
    "Hair Clipper", "Professional Clipper", "Classic Clipper", "Beard Trimmer", "Hair Trimmer", "Mini Trimmer",
    "Vintage Clipper", "Power Clipper", "Metal Clipper", "Premium Clipper", "Cordless Clipper", "Barber Pro X",
    "Titanium Clipper", "Gold Master", "Chrome Master", "Carbon Fiber Clipper", "Neon Clipper", "Cyber Trimmer",
    "Turbo Clipper", "Ultimate Clipper", "Scissors", "Electric Shaver", "Hair Dryer", "Spray Bottle",
    "Shaving Foam", "Comb", "Barber Brush"
).map { SimItem(it, Section.Barber, barberCategory(it)) }

private fun energyCategory(name: String): String = when {
    name.contains("Staff") || name.contains("Scepter") || name.contains("Mace") || name.contains("Hammer") ||
        name.contains("Spear") || name.contains("Claw") || name.contains("Blade") || name.contains("Orb") ||
        name.contains("Dragon") || name.contains("Cosmic") || name.contains("Nature") || name.contains("Chaos") -> "Fantasy"
    name.contains("Blaster") || name.contains("Cannon") -> "Laser"
    name.contains("Gauntlet") || name.contains("Rod") -> "Taser"
    else -> "Electric"
}

private fun barberCategory(name: String): String = when {
    name == "Scissors" -> "Scissors"
    name.contains("Shaver") || name.contains("Foam") -> "Shaving"
    name == "Hair Dryer" || name == "Spray Bottle" || name == "Comb" || name == "Barber Brush" -> "Salon"
    else -> "Clippers"
}

/** Only item-specific or clearly same-tool recordings are assigned. Missing exact recordings stay silent. */
private class ItemSoundController(context: Context) {
    private val soundPool: SoundPool
    private val resourceByName = mutableMapOf<String, Int>()
    private val sampleByResource = mutableMapOf<Int, Int>()
    private val loadedSamples = mutableSetOf<Int>()
    private var activeStreamId = 0
    private val loopingNames = setOf("Hair Dryer", "Ice Scepter", "Plasma Staff", "Dragon Staff", "Electric Fan", "Cosmic Staff", "Energy Vortex") +
        catalog.filter { it.section == Section.Barber && it.category == "Clippers" }.map { it.name }

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(1).build()
        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) loadedSamples += sampleId
        }

        resourceByName["AK-47"] = R.raw.pixabay_ak47_8760
        resourceByName["Desert Eagle"] = R.raw.pixabay_desert_eagle_14622
        catalog.filter { it.section == Section.Barber && it.category == "Clippers" }.forEach {
            resourceByName[it.name] = R.raw.pixabay_hair_clipper_63593
        }
        resourceByName["Scissors"] = R.raw.pixabay_scissors_barber_411614
        resourceByName["Hair Dryer"] = R.raw.barber_mixkit_1894

        val energyResources = listOf(
            R.raw.energy_mixkit_2596, R.raw.energy_mixkit_2602, R.raw.energy_mixkit_2595,
            R.raw.energy_mixkit_2132, R.raw.energy_mixkit_2599, R.raw.energy_mixkit_2600,
            R.raw.energy_mixkit_1455, R.raw.energy_mixkit_2598, R.raw.energy_mixkit_3201,
            R.raw.energy_mixkit_3213, R.raw.energy_mixkit_2594, R.raw.energy_mixkit_2603,
            R.raw.energy_mixkit_2592, R.raw.energy_mixkit_904, R.raw.energy_mixkit_2138,
            R.raw.energy_mixkit_1453, R.raw.energy_mixkit_2939, R.raw.energy_mixkit_910,
            R.raw.energy_mixkit_2131, R.raw.energy_mixkit_2597, R.raw.energy_mixkit_2601,
            R.raw.energy_mixkit_2591, R.raw.energy_mixkit_2593, R.raw.energy_mixkit_3180
        )
        catalog.filter { it.section == Section.Energy }.forEachIndexed { index, item ->
            resourceByName[item.name] = energyResources[index]
        }
        resourceByName.values.distinct().forEach { resourceId ->
            sampleByResource[resourceId] = soundPool.load(context, resourceId, 1)
        }
    }

    fun hasSound(item: SimItem): Boolean = resourceByName.containsKey(item.name)

    fun play(item: SimItem): Int {
        stop()
        val resourceId = resourceByName[item.name] ?: return 0
        val sampleId = sampleByResource[resourceId] ?: return 0
        if (sampleId == 0 || sampleId !in loadedSamples) return 0
        val loop = if (item.name in loopingNames) -1 else 0
        activeStreamId = soundPool.play(sampleId, 1f, 1f, 1, loop, 1f)
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
    val soundController = remember(context) { ItemSoundController(context) }
    DisposableEffect(soundController) { onDispose { soundController.release() } }
    var destination by remember { mutableStateOf(Destination.Home) }
    var favorites by remember { mutableStateOf(setOf<String>()) }
    var safeMode by remember { mutableStateOf(false) }
    var screenFlash by remember { mutableStateOf(true) }
    var vibration by remember { mutableStateOf(true) }
    var flashStrength by remember { mutableIntStateOf(62) }
    var gameScore by remember { mutableIntStateOf(0) }

    BackHandler(destination != Destination.Home) {
        destination = if (destination in setOf(Destination.Favorites, Destination.MiniGames, Destination.Gallery, Destination.Premium)) {
            Destination.Home
        } else Destination.Home
        soundController.stop()
    }

    MaterialTheme(colorScheme = androidx.compose.material3.darkColorScheme(
        background = Background, surface = Panel, primary = Red, secondary = Blue
    )) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                SimulatorNavigation(destination) { destination = it; soundController.stop() }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding).background(Background)) {
                when (destination) {
                    Destination.Home -> HomeScreen(
                        onSection = { destination = it.toDestination() },
                        onUtility = { destination = it },
                        onSettings = { destination = Destination.Settings }
                    )
                    Destination.Weapons, Destination.Energy, Destination.Barber -> {
                        val section = destination.toSection()!!
                        CatalogScreen(
                            section = section,
                            data = catalog.filter { it.section == section },
                            favorites = favorites,
                            soundController = soundController,
                            onFavorite = { name -> favorites = toggle(favorites, name) },
                            onBack = { destination = Destination.Home }
                        )
                    }
                    Destination.Favorites -> CatalogScreen(
                        section = null,
                        data = catalog.filter { it.name in favorites },
                        favorites = favorites,
                        soundController = soundController,
                        onFavorite = { name -> favorites = toggle(favorites, name) },
                        onBack = { destination = Destination.Home }
                    )
                    Destination.Gallery -> GalleryScreen(catalog, onBack = { destination = Destination.Home })
                    Destination.Settings -> SettingsScreen(
                        safeMode, { safeMode = it }, screenFlash, { screenFlash = it },
                        vibration, { vibration = it }, flashStrength, { flashStrength = it },
                        onBack = { destination = Destination.Home }
                    )
                    Destination.MiniGames -> MiniGamesScreen(gameScore, { gameScore += 1 }, { destination = Destination.Home })
                    Destination.Premium -> PremiumScreen { destination = Destination.Home }
                }
            }
        }
    }
}

private fun Destination.toSection(): Section? = when (this) {
    Destination.Weapons -> Section.Weapons
    Destination.Energy -> Section.Energy
    Destination.Barber -> Section.Barber
    else -> null
}

private fun Section.toDestination(): Destination = when (this) {
    Section.Weapons -> Destination.Weapons
    Section.Energy -> Destination.Energy
    Section.Barber -> Destination.Barber
}

private fun toggle(values: Set<String>, name: String): Set<String> =
    if (name in values) values - name else values + name

@Composable
private fun SimulatorNavigation(selected: Destination, onSelect: (Destination) -> Unit) {
    val navItems = listOf(
        Destination.Home to Icons.Default.Home,
        Destination.Weapons to Icons.Default.Shield,
        Destination.Energy to Icons.Default.Bolt,
        Destination.Barber to Icons.Default.ContentCut,
        Destination.Settings to Icons.Default.Settings
    )
    NavigationBar(
        containerColor = Color(0xFF0C1320),
        contentColor = Color.White,
        tonalElevation = 0.dp
    ) {
        navItems.forEach { (destination, icon) ->
            val tint = destination.toSection()?.tint ?: Gold
            NavigationBarItem(
                selected = selected == destination,
                onClick = { onSelect(destination) },
                icon = { Icon(icon, contentDescription = destination.label, modifier = Modifier.size(22.dp)) },
                label = { Text(destination.label, fontSize = 10.sp, maxLines = 1) },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = tint,
                    selectedTextColor = tint,
                    indicatorColor = tint.copy(alpha = .13f),
                    unselectedIconColor = Muted,
                    unselectedTextColor = Muted
                )
            )
        }
    }
}

@Composable
private fun HomeScreen(
    onSection: (Section) -> Unit,
    onUtility: (Destination) -> Unit,
    onSettings: () -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundIconButton(Icons.Default.FavoriteBorder, "Favorites", Red) { onUtility(Destination.Favorites) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("WEAPON & BARBER", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
                Text("S I M U L A T O R", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.2.sp)
            }
            RoundIconButton(Icons.Default.Settings, "Settings", Blue, onSettings)
        }
        HomeHeroBanner(onClick = { onSection(Section.Weapons) })
        Row(
            Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("CHOOSE YOUR EXPERIENCE", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.1.sp)
            Text("03 CATEGORIES", color = Muted, fontSize = 9.sp, letterSpacing = 1.3.sp)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Section.values().forEach { section ->
                HomeSectionCard(section, Modifier.weight(1f)) { onSection(section) }
            }
        }
        Text("QUICK ACCESS", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, modifier = Modifier.padding(top = 15.dp, bottom = 8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            UtilityTile("Favorites", Icons.Default.Favorite, Red, Modifier.weight(1f)) { onUtility(Destination.Favorites) }
            UtilityTile("Mini Games", Icons.Default.Games, Blue, Modifier.weight(1f)) { onUtility(Destination.MiniGames) }
            UtilityTile("Gallery", Icons.Default.PhotoLibrary, Purple, Modifier.weight(1f)) { onUtility(Destination.Gallery) }
            UtilityTile("Premium", Icons.Default.WorkspacePremium, Gold, Modifier.weight(1f)) { onUtility(Destination.Premium) }
        }
        Spacer(Modifier.height(14.dp))
        TechFooter(Modifier.fillMaxWidth())
    }
}

@Composable
private fun HomeHeroBanner(onClick: () -> Unit) {
    val item = catalog.first()
    val context = LocalContext.current
    val imageId = remember(item) { context.resources.getIdentifier(item.drawableName, "drawable", context.packageName) }
    Box(
        Modifier.fillMaxWidth().height(168.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, Red.copy(alpha = .8f), RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF271018), Color(0xFF101A2D), Color(0xFF060A13))))
            .clickable(onClick = onClick)
    ) {
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Red.copy(alpha = .15f), Color.Transparent, Blue.copy(alpha = .14f)))))
        if (imageId != 0) {
            Image(painterResource(imageId), contentDescription = "AK-47", modifier = Modifier.align(Alignment.CenterEnd).size(width = 230.dp, height = 166.dp).padding(end = 2.dp), contentScale = androidx.compose.ui.layout.ContentScale.Fit)
        } else {
            Icon(Icons.Default.Shield, null, tint = Red.copy(alpha = .42f), modifier = Modifier.align(Alignment.CenterEnd).size(124.dp).padding(end = 24.dp))
        }
        Column(Modifier.align(Alignment.CenterStart).padding(start = 18.dp, end = 130.dp)) {
            Text("WEAPON & BARBER", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
            Text("SIMULATOR", color = Gold, fontSize = 25.sp, fontWeight = FontWeight.Black, letterSpacing = .5.sp)
            Text("TWO WORLDS · ONE APP", color = Muted, fontSize = 9.sp, letterSpacing = 1.1.sp, modifier = Modifier.padding(top = 4.dp))
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(34.dp).clip(CircleShape).background(Red), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Text("  START SIMULATOR", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun HomeSectionCard(section: Section, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    val imageId = remember(section) {
        val first = catalog.first { it.section == section }
        context.resources.getIdentifier(first.drawableName, "drawable", context.packageName)
    }
    Column(
        modifier.height(190.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(1.2.dp, section.tint.copy(alpha = .92f), RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(listOf(section.tint.copy(alpha = .21f), Panel, Color(0xFF0B101B))))
            .clickable(onClick = onClick)
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
            Box(Modifier.fillMaxSize().background(Brush.radialGradient(listOf(section.tint.copy(alpha = .28f), Color.Transparent))))
            if (imageId != 0) Image(painterResource(imageId), contentDescription = section.title, modifier = Modifier.fillMaxSize().padding(3.dp), contentScale = androidx.compose.ui.layout.ContentScale.Fit)
            else Icon(sectionIcon(section), null, tint = section.tint, modifier = Modifier.size(48.dp))
        }
        Text(section.title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(section.subtitle, color = section.tint, fontSize = 7.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, maxLines = 2, lineHeight = 9.sp)
        Spacer(Modifier.height(6.dp))
        Box(Modifier.size(34.dp).clip(CircleShape).background(section.tint.copy(alpha = .22f)).border(1.dp, section.tint, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
private fun UtilityTile(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.height(84.dp).clip(RoundedCornerShape(16.dp))
            .border(1.dp, tint.copy(alpha = .48f), RoundedCornerShape(16.dp))
            .background(Panel).clickable(onClick = onClick).padding(vertical = 9.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(25.dp))
        Spacer(Modifier.height(5.dp))
        Text(label, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun RoundIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: Color, onClick: () -> Unit) {
    Box(
        Modifier.size(42.dp).clip(CircleShape).border(1.dp, tint.copy(alpha = .55f), CircleShape)
            .background(Panel).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(21.dp)) }
}

@Composable
private fun CatalogScreen(
    section: Section?,
    data: List<SimItem>,
    favorites: Set<String>,
    soundController: ItemSoundController,
    onFavorite: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedCategory by remember(section) { mutableStateOf("All") }
    var activeItem by remember { mutableStateOf<String?>(null) }
    val toastContext = LocalContext.current
    DisposableEffect(Unit) { onDispose { soundController.stop() } }
    val title = section?.title ?: "Favorites"
    val accent = section?.tint ?: Gold
    val categories = when (section) {
        Section.Weapons -> listOf("All", "Pistols", "Assault Rifles", "SMG", "Shotguns", "Snipers", "Special")
        Section.Energy -> listOf("All", "Laser", "Taser", "Electric", "Fantasy")
        Section.Barber -> listOf("All", "Clippers", "Scissors", "Shaving", "Salon")
        null -> listOf("All")
    }
    val visibleItems = if (selectedCategory == "All") data else data.filter { it.category == selectedCategory }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundIconButton(Icons.Default.ArrowBack, "Back", accent, onBack)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title.uppercase(), color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Text(if (section == null) "SAVED ITEMS" else section.subtitle, color = accent, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
            }
            Text("${data.size.toString().padStart(2, '0')}", color = accent, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }
        if (section != null) SectionHeader(section, data.firstOrNull())
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(if (category == "All") "All ${section?.let { if (it == Section.Weapons) "Weapons" else "Tools" } ?: "Items"}" else category, fontSize = 11.sp, maxLines = 1) },
                    leadingIcon = if (category == "All" && selectedCategory == category) ({ Icon(section?.let { sectionIcon(it) } ?: Icons.Default.Star, null, modifier = Modifier.size(14.dp)) }) else null,
                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                        selectedContainerColor = accent.copy(alpha = .17f),
                        selectedLabelColor = Color.White,
                        labelColor = Muted
                    ),
                    border = androidx.compose.material3.FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedCategory == category,
                        borderColor = PanelRaised,
                        selectedBorderColor = accent
                    )
                )
            }
        }
        if (visibleItems.isEmpty()) {
            EmptyState(title = if (section == null) "No favorites yet" else "No items in this filter", accent = accent)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(visibleItems, key = { it.name }) { item ->
                    ItemCard(
                        item = item,
                        accent = item.section.tint,
                        isFavorite = item.name in favorites,
                        active = activeItem == item.name,
                        hasSound = soundController.hasSound(item),
                        onFavorite = { onFavorite(item.name) },
                        onPress = {
                            activeItem = item.name
                            if (!soundController.hasSound(item)) {
                                Toast.makeText(toastContext, "Sound not available for this item yet", Toast.LENGTH_SHORT).show()
                            }
                            soundController.play(item)
                        },
                        onRelease = { soundController.stop(); activeItem = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(section: Section, featured: SimItem?) {
    val context = LocalContext.current
    val imageId = remember(featured) {
        featured?.let { context.resources.getIdentifier(it.drawableName, "drawable", context.packageName) } ?: 0
    }
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp).height(112.dp)
            .clip(RoundedCornerShape(20.dp)).border(1.dp, section.tint.copy(alpha = .6f), RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(listOf(section.tint.copy(alpha = .22f), Panel, Color(0xFF0B111B))))
    ) {
        Box(Modifier.fillMaxSize().background(Brush.radialGradient(listOf(section.tint.copy(alpha = .15f), Color.Transparent))))
        if (imageId != 0) Image(painterResource(imageId), contentDescription = null, modifier = Modifier.align(Alignment.CenterEnd).size(width = 160.dp, height = 110.dp).padding(end = 7.dp), contentScale = androidx.compose.ui.layout.ContentScale.Fit)
        Column(Modifier.align(Alignment.CenterStart).padding(start = 17.dp, end = 135.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(sectionIcon(section), null, tint = section.tint, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(7.dp))
                Text(section.title.uppercase(), color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Black, letterSpacing = .9.sp)
            }
            Text(section.subtitle, color = section.tint, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp, modifier = Modifier.padding(top = 4.dp))
            Text("CHOOSE YOUR FAVORITE", color = Muted, fontSize = 8.sp, letterSpacing = 1.2.sp, modifier = Modifier.padding(top = 7.dp))
        }
    }
}

@Composable
private fun ItemCard(
    item: SimItem,
    accent: Color,
    isFavorite: Boolean,
    active: Boolean,
    hasSound: Boolean,
    onFavorite: () -> Unit,
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    val context = LocalContext.current
    val imageId = remember(item.drawableName) { context.resources.getIdentifier(item.drawableName, "drawable", context.packageName) }
    val cardColor by animateColorAsState(if (active) accent.copy(alpha = .17f) else Panel, label = "card")
    Card(
        modifier = Modifier.fillMaxWidth().border(1.dp, accent.copy(alpha = if (active) .95f else .68f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(Modifier.padding(8.dp)) {
            Box(
                Modifier.fillMaxWidth().height(114.dp).clip(RoundedCornerShape(14.dp))
                    .background(Brush.radialGradient(listOf(accent.copy(alpha = .27f), Color(0xFF090E18), Color(0xFF0B101B))))
                    .pointerInput(item.name) {
                        detectTapGestures(onPress = {
                            onPress()
                            try { awaitRelease() } finally { onRelease() }
                        })
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageId != 0) {
                    Image(painterResource(imageId), contentDescription = item.name, modifier = Modifier.fillMaxSize().padding(2.dp), contentScale = androidx.compose.ui.layout.ContentScale.Fit)
                } else {
                    Box(Modifier.size(78.dp).clip(CircleShape).background(accent.copy(alpha = .10f)), contentAlignment = Alignment.Center) {
                        Icon(itemIcon(item), contentDescription = item.name, tint = accent, modifier = Modifier.size(42.dp))
                    }
                }
                Box(Modifier.align(Alignment.TopStart).padding(4.dp).clip(RoundedCornerShape(6.dp)).background(Color.Black.copy(alpha = .72f)).padding(horizontal = 6.dp, vertical = 3.dp)) {
                    Text(item.category.uppercase(), color = accent, fontSize = 7.sp, fontWeight = FontWeight.Bold, letterSpacing = .5.sp)
                }
            }
            Row(Modifier.fillMaxWidth().padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(item.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f), lineHeight = 13.sp)
                IconButton(onClick = onFavorite, modifier = Modifier.size(31.dp)) {
                    Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorite ${item.name}", tint = if (isFavorite) Red else Muted, modifier = Modifier.size(18.dp))
                }
            }
            Row(Modifier.fillMaxWidth().padding(top = 2.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(if (hasSound) "HOLD TO PLAY" else "VISUAL ONLY", color = if (hasSound) accent else Muted, fontSize = 7.sp, fontWeight = FontWeight.Bold, letterSpacing = .7.sp)
                Box(Modifier.size(28.dp).clip(CircleShape).border(1.dp, accent, CircleShape).background(accent.copy(alpha = .13f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun EmptyState(title: String, accent: Color) {
    Column(Modifier.fillMaxSize().padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.Star, null, tint = accent, modifier = Modifier.size(52.dp))
        Spacer(Modifier.height(10.dp))
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Items you save will appear here.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 5.dp))
    }
}

@Composable
private fun GalleryScreen(items: List<SimItem>, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ScreenTitle("Gallery", "ALL 81 CATALOG ITEMS", Purple, onBack)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(116.dp),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items, key = { it.name }) { item ->
                val context = LocalContext.current
                val imageId = remember(item.drawableName) { context.resources.getIdentifier(item.drawableName, "drawable", context.packageName) }
                Column(Modifier.clip(RoundedCornerShape(14.dp)).border(1.dp, item.section.tint.copy(alpha = .7f), RoundedCornerShape(14.dp)).background(Panel).padding(6.dp)) {
                    Box(Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(10.dp)).background(item.section.tint.copy(alpha = .10f)), contentAlignment = Alignment.Center) {
                        if (imageId != 0) Image(painterResource(imageId), item.name, modifier = Modifier.fillMaxSize().padding(3.dp), contentScale = androidx.compose.ui.layout.ContentScale.Fit)
                        else Icon(itemIcon(item), item.name, tint = item.section.tint, modifier = Modifier.size(34.dp))
                    }
                    Text(item.name, color = Color.White, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 5.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    safeMode: Boolean,
    onSafeMode: (Boolean) -> Unit,
    screenFlash: Boolean,
    onScreenFlash: (Boolean) -> Unit,
    vibration: Boolean,
    onVibration: (Boolean) -> Unit,
    flashStrength: Int,
    onFlashStrength: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        ScreenTitle("Settings", "SIMULATOR PREFERENCES", Blue, onBack)
        SettingsPanel {
            SettingToggle("Screen effects", "Visual pulse when an item is pressed", screenFlash, onScreenFlash)
            SettingToggle("Vibration", "Tactile response while holding an item", vibration, onVibration)
            SettingToggle("Safe effects mode", "Reduce bright or rapid effects", safeMode, onSafeMode)
            Text("FLASH INTENSITY  ·  $flashStrength%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
            Slider(value = flashStrength.toFloat(), onValueChange = { onFlashStrength(it.toInt()) }, valueRange = 0f..100f)
        }
        SettingsPanel(Modifier.padding(top = 12.dp)) {
            Text("LANGUAGE", color = Blue, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.6.sp)
            Text("English   /   العربية", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 7.dp))
            Text("The current screen follows the supplied English visual reference.", color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
private fun SettingToggle(title: String, description: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f).padding(end = 10.dp)) {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(description, color = Muted, fontSize = 10.sp, lineHeight = 13.sp)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun SettingsPanel(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).border(1.dp, PanelRaised, RoundedCornerShape(20.dp)).background(Panel).padding(16.dp), content = content)
}

@Composable
private fun MiniGamesScreen(score: Int, onTapTarget: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        ScreenTitle("Mini Games", "QUICK REFLEX CHALLENGE", Blue, onBack)
        Spacer(Modifier.height(14.dp))
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
            Column(Modifier.fillMaxWidth().padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("TARGET PRACTICE", color = Blue, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.6.sp)
                Text("Score  $score", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 12.dp))
                Box(Modifier.padding(top = 22.dp).size(170.dp).clip(CircleShape).border(2.dp, Red, CircleShape).background(Brush.radialGradient(listOf(Red.copy(alpha = .3f), Panel))), contentAlignment = Alignment.Center) {
                    Box(Modifier.size(92.dp).clip(CircleShape).border(2.dp, Blue, CircleShape).clickable(onClick = onTapTarget), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Bolt, "Tap target", tint = Blue, modifier = Modifier.size(42.dp))
                    }
                }
                Text("Tap the glowing target to score", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 18.dp))
            }
        }
    }
}

@Composable
private fun PremiumScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        ScreenTitle("Premium", "UPGRADE THE SIMULATOR", Gold, onBack)
        Spacer(Modifier.height(14.dp))
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF211B10))) {
            Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.WorkspacePremium, null, tint = Gold, modifier = Modifier.size(58.dp))
                Text("PREMIUM EDITION", color = Gold, fontSize = 21.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 10.dp))
                Text("More collections · custom themes · expanded effects", color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                Text("Premium purchase is not connected in this build.", color = Muted, textAlign = TextAlign.Center, fontSize = 10.sp, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

@Composable
private fun ScreenTitle(title: String, subtitle: String, tint: Color, onBack: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        RoundIconButton(Icons.Default.ArrowBack, "Back", tint, onBack)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title.uppercase(), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            Text(subtitle, color = tint, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
        }
    }
}

@Composable
private fun TechFooter(modifier: Modifier = Modifier) {
    Row(modifier.height(20.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1f).height(1.dp).background(Brush.horizontalGradient(listOf(Color.Transparent, Red, Blue))))
        Box(Modifier.padding(horizontal = 9.dp).width(44.dp).height(5.dp).clip(RoundedCornerShape(10.dp)).background(Blue))
        Box(Modifier.weight(1f).height(1.dp).background(Brush.horizontalGradient(listOf(Blue, Purple, Color.Transparent))))
    }
}

private fun sectionIcon(section: Section) = when (section) {
    Section.Weapons -> Icons.Default.Shield
    Section.Energy -> Icons.Default.Bolt
    Section.Barber -> Icons.Default.ContentCut
}

private fun itemIcon(item: SimItem) = when {
    item.name == "Scissors" -> Icons.Default.ContentCut
    item.section == Section.Weapons -> Icons.Default.Shield
    item.section == Section.Energy -> Icons.Default.FlashOn
    item.name.contains("Comb") || item.name.contains("Brush") -> Icons.Default.Star
    else -> Icons.Default.FlashOn
}
