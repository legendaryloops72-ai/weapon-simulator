package com.weapon.simulator.model

import androidx.compose.ui.graphics.Color
import com.weapon.simulator.R

val ThemeDarkBg = Color(0xFF05070D)
val ThemeCardPanel = Color(0xFF101524)
val ThemeCardPanelRaised = Color(0xFF161E33)
val AccentRed = Color(0xFFFF334F)
val AccentCyan = Color(0xFF16CFFF)
val AccentPurple = Color(0xFFC039FF)
val AccentGold = Color(0xFFFFB800)
val TextMuted = Color(0xFF8F9FB5)

enum class Section(val title: String, val subtitle: String) {
    Weapons("Weapons", "23 VIDEO WEAPONS"),
    Energy("Energy Tools", "24 ELECTRIC EFFECTS"),
    Barber("Barber Tools", "27 PROFESSIONAL TOOLS")
}

val Section.tint: Color
    get() = when (this) {
        Section.Weapons -> AccentRed
        Section.Energy -> AccentCyan
        Section.Barber -> AccentPurple
    }

enum class Destination(val label: String) {
    Home("Home"),
    Weapons("Weapons"),
    Energy("Energy"),
    Barber("Barber"),
    Favorites("Favorites"),
    MiniGames("Mini Games"),
    Gallery("Gallery"),
    Settings("Settings"),
    Premium("Premium")
}

data class SimItem(
    val name: String,
    val section: Section,
    val category: String,
    val drawableRes: Int,
    val assetPath: String = ""
)

val catalogItems: List<SimItem> = buildList {
    val weaponNames = listOf(
        "Glock" to "Pistols", "M1911" to "Pistols", "Sig P320" to "Pistols",
        "Desert Eagle" to "Pistols", "Beretta M9" to "Pistols", "Makarov" to "Pistols",
        "S&W Model 19" to "Pistols", "Browning Citori 725" to "Shotguns",
        "Remington 870" to "Shotguns", "M26 MASS" to "Shotguns", "DP-12" to "Shotguns",
        "Origin 12" to "Shotguns", "AA-12" to "Shotguns", "Mk 14 EBR" to "Assault Rifles",
        "SKS" to "Assault Rifles", "FN FAL" to "Assault Rifles", "Dragunov" to "Snipers",
        "Barrett" to "Snipers", "Marlin Model 336" to "Snipers", "Kar 98k" to "Snipers",
        "R700" to "Snipers", "HDR" to "Snipers", "AX50" to "Snipers"
    )
    val weaponDrawables = listOf(
        R.drawable.weapon_01, R.drawable.weapon_02, R.drawable.weapon_03, R.drawable.weapon_04,
        R.drawable.weapon_05, R.drawable.weapon_06, R.drawable.weapon_07, R.drawable.weapon_08,
        R.drawable.weapon_09, R.drawable.weapon_10, R.drawable.weapon_11, R.drawable.weapon_12,
        R.drawable.weapon_13, R.drawable.weapon_14, R.drawable.weapon_15, R.drawable.weapon_16,
        R.drawable.weapon_17, R.drawable.weapon_18, R.drawable.weapon_19, R.drawable.weapon_20,
        R.drawable.weapon_21, R.drawable.weapon_22, R.drawable.weapon_23
    )
    weaponNames.forEachIndexed { index, (name, category) ->
        val res = weaponDrawables[index]
        val num = String.format("%02d", index + 1)
        add(SimItem(name, Section.Weapons, category, res, "images/weapons/weapon_$num.webp"))
    }

    val energyNames = listOf(
        "Thunder Blade", "Golden Scepter", "Void Staff", "Ice Scepter", "Crimson Mace", "Lightning Orb",
        "Electric Gauntlet", "Storm Spear", "Tesla Rod", "Lightning Whip", "Shadow Claw", "Holy Hammer",
        "Crystal Staff", "Plasma Staff", "Dragon Staff", "Nature Lightning", "Chaos Staff", "Electric Fan",
        "Cosmic Staff", "Dual Lightning Blades", "Lightning Blaster", "Energy Vortex", "Lightning Lance", "Thunder Tornado"
    )
    val energyDrawables = listOf(
        R.drawable.energy_01, R.drawable.energy_02, R.drawable.energy_03, R.drawable.energy_04,
        R.drawable.energy_05, R.drawable.energy_06, R.drawable.energy_07, R.drawable.energy_08,
        R.drawable.energy_09, R.drawable.energy_10, R.drawable.energy_11, R.drawable.energy_12,
        R.drawable.energy_13, R.drawable.energy_14, R.drawable.energy_15, R.drawable.energy_16,
        R.drawable.energy_17, R.drawable.energy_18, R.drawable.energy_19, R.drawable.energy_20,
        R.drawable.energy_21, R.drawable.energy_22, R.drawable.energy_23, R.drawable.energy_24
    )
    energyNames.forEachIndexed { index, name ->
        val category = when {
            name.contains("Staff") || name.contains("Scepter") || name.contains("Mace") || name.contains("Hammer") ||
                name.contains("Spear") || name.contains("Claw") || name.contains("Blade") || name.contains("Orb") -> "Fantasy"
            name.contains("Blaster") || name.contains("Cannon") -> "Laser"
            name.contains("Gauntlet") || name.contains("Rod") -> "Taser"
            else -> "Electric"
        }
        val res = energyDrawables[index]
        val num = String.format("%02d", index + 1)
        add(SimItem(name, Section.Energy, category, res, "images/energy/energy_$num.webp"))
    }

    val barberNames = listOf(
        "Hair Clipper", "Professional Clipper", "Classic Clipper", "Beard Trimmer", "Hair Trimmer", "Mini Trimmer",
        "Vintage Clipper", "Power Clipper", "Metal Clipper", "Premium Clipper", "Cordless Clipper", "Barber Pro X",
        "Titanium Clipper", "Gold Master", "Chrome Master", "Carbon Fiber Clipper", "Neon Clipper", "Cyber Trimmer",
        "Turbo Clipper", "Ultimate Clipper", "Scissors", "Electric Shaver", "Hair Dryer", "Spray Bottle",
        "Shaving Foam", "Comb", "Barber Brush"
    )
    val barberDrawables = listOf(
        R.drawable.barber_01, R.drawable.barber_02, R.drawable.barber_03, R.drawable.barber_04,
        R.drawable.barber_05, R.drawable.barber_06, R.drawable.barber_07, R.drawable.barber_08,
        R.drawable.barber_09, R.drawable.barber_10, R.drawable.barber_11, R.drawable.barber_12,
        R.drawable.barber_13, R.drawable.barber_14, R.drawable.barber_15, R.drawable.barber_16,
        R.drawable.barber_17, R.drawable.barber_18, R.drawable.barber_19, R.drawable.barber_20,
        R.drawable.barber_21, R.drawable.barber_22, R.drawable.barber_23, R.drawable.barber_24,
        R.drawable.barber_25, R.drawable.barber_26, R.drawable.barber_27
    )
    barberNames.forEachIndexed { index, name ->
        val category = when {
            name == "Scissors" -> "Scissors"
            name.contains("Shaver") || name.contains("Foam") -> "Shaving"
            name == "Hair Dryer" || name == "Spray Bottle" || name == "Comb" || name == "Barber Brush" -> "Salon"
            else -> "Clippers"
        }
        val res = barberDrawables[index]
        val num = String.format("%02d", index + 1)
        add(SimItem(name, Section.Barber, category, res, "images/barber/barber_$num.webp"))
    }
}
