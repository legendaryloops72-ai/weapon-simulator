package com.weapon.simulator

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.weapon.simulator.audio.ItemSoundController
import com.weapon.simulator.flash.FlashController
import com.weapon.simulator.model.AccentCyan
import com.weapon.simulator.model.AccentRed
import com.weapon.simulator.model.Destination
import com.weapon.simulator.model.ThemeCardPanel
import com.weapon.simulator.model.ThemeDarkBg
import com.weapon.simulator.model.catalogItems
import com.weapon.simulator.ui.CatalogScreen
import com.weapon.simulator.ui.GalleryScreen
import com.weapon.simulator.ui.HomeScreen
import com.weapon.simulator.ui.LaunchFlashOverlay
import com.weapon.simulator.ui.MiniGamesScreen
import com.weapon.simulator.ui.PremiumScreen
import com.weapon.simulator.ui.SettingsScreen

typealias Section = com.weapon.simulator.model.Section

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeaponSimulatorApp()
        }
    }
}

@Composable
private fun WeaponSimulatorApp() {
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences("weapon_sim_prefs", Context.MODE_PRIVATE) }

    val soundController = remember(context) { ItemSoundController(context, catalogItems) }
    val flashController = remember(context) { FlashController(context) }
    DisposableEffect(soundController) {
        onDispose {
            soundController.release()
            flashController.release()
        }
    }

    var destination by remember { mutableStateOf(Destination.Home) }
    var selectedSection by remember { mutableStateOf<Section?>(null) }

    var favorites by remember {
        mutableStateOf(prefs.getStringSet("favorites", emptySet()) ?: emptySet())
    }

    var safeMode by remember {
        mutableStateOf(prefs.getBoolean("safe_mode", false))
    }
    var screenFlash by remember {
        mutableStateOf(prefs.getBoolean("screen_flash", true))
    }
    var vibration by remember {
        mutableStateOf(prefs.getBoolean("vibration", true))
    }
    var flashStrength by remember {
        mutableFloatStateOf(prefs.getFloat("flash_strength", 0.65f))
    }
    var externalFlash by remember {
        mutableStateOf(prefs.getBoolean("external_flash", false))
    }
    var launchPulseId by remember { mutableIntStateOf(0) }

    fun triggerHaptic() {
        if (!vibration) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib?.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vib?.vibrate(35)
                }
            }
        } catch (_: Exception) {}
    }

    fun toggleFavorite(name: String) {
        val next = if (name in favorites) favorites - name else favorites + name
        favorites = next
        prefs.edit().putStringSet("favorites", next).apply()
        triggerHaptic()
    }

    fun requestCameraPermissionIfNeeded() {
        val activity = context as? Activity ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            activity.requestPermissions(arrayOf(Manifest.permission.CAMERA), 4101)
        }
    }

    fun launchItem(item: com.weapon.simulator.model.SimItem) {
        soundController.play(item)
        triggerHaptic()
        launchPulseId += 1
        if (externalFlash) {
            requestCameraPermissionIfNeeded()
            flashController.pulse(enabled = true)
        }
    }

    fun stopLaunch() {
        soundController.stop()
        flashController.stop()
    }

    BackHandler(destination != Destination.Home) {
        soundController.stop()
        destination = Destination.Home
        selectedSection = null
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = ThemeDarkBg,
            surface = ThemeCardPanel,
            primary = AccentRed,
            secondary = AccentCyan
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = ThemeDarkBg
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (destination) {
                    Destination.Home -> {
                        HomeScreen(
                            onNavigateSection = { sec ->
                                selectedSection = sec
                                destination = when (sec) {
                                    Section.Weapons -> Destination.Weapons
                                    Section.Energy -> Destination.Energy
                                    Section.Barber -> Destination.Barber
                                }
                            },
                            onNavigateDestination = { dest ->
                                destination = dest
                            }
                        )
                    }

                    Destination.Weapons, Destination.Energy, Destination.Barber -> {
                        val sec = selectedSection ?: when (destination) {
                            Destination.Weapons -> Section.Weapons
                            Destination.Energy -> Section.Energy
                            else -> Section.Barber
                        }
                        CatalogScreen(
                            section = sec,
                            items = catalogItems.filter { it.section == sec },
                            favorites = favorites,
                            soundController = soundController,
                            onToggleFavorite = { toggleFavorite(it) },
                            onPerformHaptic = { triggerHaptic() },
                            onLaunch = { launchItem(it) },
                            onStopLaunch = { stopLaunch() },
                            onBack = {
                                destination = Destination.Home
                                selectedSection = null
                            }
                        )
                    }

                    Destination.Favorites -> {
                        CatalogScreen(
                            section = null,
                            items = catalogItems.filter { it.name in favorites },
                            favorites = favorites,
                            soundController = soundController,
                            onToggleFavorite = { toggleFavorite(it) },
                            onPerformHaptic = { triggerHaptic() },
                            onLaunch = { launchItem(it) },
                            onStopLaunch = { stopLaunch() },
                            onBack = { destination = Destination.Home }
                        )
                    }

                    Destination.Settings -> {
                        SettingsScreen(
                            safeMode = safeMode,
                            onSafeModeChange = {
                                safeMode = it
                                prefs.edit().putBoolean("safe_mode", it).apply()
                            },
                            screenFlash = screenFlash,
                            onScreenFlashChange = {
                                screenFlash = it
                                prefs.edit().putBoolean("screen_flash", it).apply()
                            },
                            vibration = vibration,
                            onVibrationChange = {
                                vibration = it
                                prefs.edit().putBoolean("vibration", it).apply()
                            },
                            flashStrength = flashStrength,
                            onFlashStrengthChange = {
                                flashStrength = it
                                prefs.edit().putFloat("flash_strength", it).apply()
                            },
                            externalFlash = externalFlash,
                            onExternalFlashChange = {
                                externalFlash = it
                                prefs.edit().putBoolean("external_flash", it).apply()
                                if (!it) flashController.stop()
                            },
                            onBack = { destination = Destination.Home }
                        )
                    }

                    Destination.MiniGames -> {
                        MiniGamesScreen(
                            soundController = soundController,
                            onPerformHaptic = { triggerHaptic() },
                            onBack = { destination = Destination.Home }
                        )
                    }

                    Destination.Gallery -> {
                        GalleryScreen(
                            items = catalogItems,
                            soundController = soundController,
                            onPerformHaptic = { triggerHaptic() },
                            onBack = { destination = Destination.Home }
                        )
                    }

                    Destination.Premium -> {
                        PremiumScreen(onBack = { destination = Destination.Home })
                    }
                }
                LaunchFlashOverlay(
                    pulseId = launchPulseId,
                    enabled = screenFlash,
                    strength = flashStrength
                )
            }
        }
    }
}
