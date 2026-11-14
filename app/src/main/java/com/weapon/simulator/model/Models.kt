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
    Barber("Barber Tools", "5 PROFESSIONAL TOOLS")
}

val Section.tint: Color
    get() = when (this) {
        Section.Weapons -> AccentRed
        Section.Barber -> AccentPurple
    }

enum class Destination(val label: String) {
    Home("Home"),
    Weapons("Weapons"),
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

    val barberNames = listOf(
        "Hair Clipper", "Scissors", "Comb", "Spray Bottle", "Professional Clipper"
    )
    val barberDrawables = listOf(
        R.drawable.barber_01, R.drawable.barber_21, R.drawable.comb, R.drawable.barber_24, R.drawable.barber_02
    )
    barberNames.forEachIndexed { index, name ->
        val category = when {
            name == "Scissors" -> "Scissors"
            name == "Comb" -> "Comb"
            name == "Spray Bottle" -> "Spray"
            else -> "Clippers"
        }
        val res = barberDrawables[index]
        val asset = when (name) {
            "Scissors" -> "images/barber/barber_21.webp"
            "Comb" -> "images/barber/comb.webp"
            "Spray Bottle" -> "images/barber/barber_24.webp"
            "Professional Clipper" -> "images/barber/barber_02.webp"
            else -> "images/barber/barber_01.webp"
        }
        add(SimItem(name, Section.Barber, category, res, asset))
    }
}
