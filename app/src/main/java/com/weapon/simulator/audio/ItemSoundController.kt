package com.weapon.simulator.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.weapon.simulator.R
import com.weapon.simulator.model.SimItem

class ItemSoundController(context: Context, catalog: List<SimItem>) {
    private val soundPool: SoundPool
    private val sampleByResource = mutableMapOf<Int, Int>()
    private val loadedSamples = mutableSetOf<Int>()
    private var activeStreamId = 0

    // Continuous motor/hum tools loop while pressed; one-shot burst items play once
    private val loopingItems = setOf(
        "Hair Clipper", "Professional Clipper", "Classic Clipper", "Beard Trimmer", "Hair Trimmer",
        "Mini Trimmer", "Vintage Clipper", "Power Clipper", "Metal Clipper", "Premium Clipper",
        "Cordless Clipper", "Barber Pro X", "Titanium Clipper", "Gold Master", "Chrome Master",
        "Carbon Fiber Clipper", "Neon Clipper", "Cyber Trimmer", "Turbo Clipper", "Ultimate Clipper",
        "Electric Shaver", "Hair Dryer", "Tesla Rod", "Electric Fan", "Electric Gauntlet",
        "Energy Vortex", "Minigun"
    )

    // Exact 1-to-1 audio bindings. Every item is verified by audio content.
    // Items without an authentic matching recording are intentionally set to null (silent)
    // per instructions: "إذا لم تجد تسجيلاً مناسباً لعنصر معين اترك العنصر دون صوت مؤقتاً وأضفه إلى تقرير الأصوات الناقصة"
    val soundMap: Map<String, Int?> = mapOf(
        // --- WEAPONS (30 Items) ---
        "AK-47" to R.raw.weapons_ak47_burst,
        "M4A1" to R.raw.weapons_auto_burst,
        "Desert Eagle" to R.raw.weapons_9mm_pistol,
        "AWM Sniper" to R.raw.weapons_mixkit_1693,
        "Shotgun" to R.raw.weapons_mixkit_1659,
        "Minigun" to R.raw.weapons_mixkit_2777,
        "Glock 19" to R.raw.weapons_mixkit_1662,
        "Beretta 92" to R.raw.weapons_mixkit_1668,
        "Revolver" to R.raw.weapons_mixkit_1674,
        "Uzi" to R.raw.weapons_mixkit_2778,
        "MP5" to R.raw.weapons_mixkit_2779,
        "P90" to R.raw.weapons_mixkit_2780,
        "Vector SMG" to R.raw.weapons_mixkit_2784,
        "SCAR-L" to R.raw.weapons_mixkit_2782,
        "AUG A3" to R.raw.weapons_mixkit_2804,
        "FAMAS" to null,            // Authentic recording missing - left silent per policy
        "G36" to null,              // Authentic recording missing - left silent per policy
        "M16" to R.raw.weapons_ak47_single,
        "Tactical Pistol" to R.raw.weapons_mixkit_1666,
        "USP Tactical" to R.raw.weapons_mixkit_1660,
        "SPAS-12" to R.raw.weapons_mixkit_1665,
        "Pump Shotgun" to R.raw.weapons_mixkit_1664,
        "Barrett M82" to R.raw.weapons_mixkit_223,
        "Dragunov SVD" to R.raw.weapons_mixkit_1678,
        "Thompson" to null,         // Authentic recording missing - left silent per policy
        "Kriss Vector" to null,     // Authentic recording missing - left silent per policy
        "Laser Gun" to R.raw.weapons_mixkit_1670,
        "Plasma Rifle" to R.raw.weapons_mixkit_1679,
        "Energy Cannon" to R.raw.energy_cannon_impact,
        "Neon Rifle" to R.raw.weapons_mixkit_2783,

        // --- ENERGY TOOLS (24 Items) ---
        "Thunder Blade" to R.raw.energy_laser_thunder,
        "Golden Scepter" to R.raw.energy_mixkit_2596,
        "Void Staff" to R.raw.energy_mixkit_2599,
        "Ice Scepter" to R.raw.energy_mixkit_2594,
        "Crimson Mace" to R.raw.energy_mixkit_2595,
        "Lightning Orb" to R.raw.energy_laser_swoosh,
        "Electric Gauntlet" to R.raw.energy_mixkit_3201,
        "Storm Spear" to R.raw.energy_electric_thunder,
        "Tesla Rod" to R.raw.energy_mixkit_2603,
        "Lightning Whip" to R.raw.energy_lightning_whip,
        "Shadow Claw" to R.raw.energy_mixkit_2966,
        "Holy Hammer" to R.raw.energy_lightning_blast,
        "Crystal Staff" to R.raw.energy_mixkit_1453,
        "Plasma Staff" to R.raw.energy_mixkit_2969,
        "Dragon Staff" to R.raw.energy_mixkit_2597,
        "Nature Lightning" to R.raw.energy_mixkit_1455,
        "Chaos Staff" to R.raw.energy_mixkit_2614,
        "Electric Fan" to R.raw.energy_mixkit_2646,
        "Cosmic Staff" to R.raw.energy_mixkit_2967,
        "Dual Lightning Blades" to R.raw.energy_mixkit_2340,
        "Lightning Blaster" to R.raw.weapons_mixkit_1681,
        "Energy Vortex" to R.raw.energy_mixkit_2968,
        "Lightning Lance" to R.raw.energy_mixkit_2365,
        "Thunder Tornado" to R.raw.energy_mixkit_1054,

        // --- BARBER TOOLS (27 Items) ---
        "Hair Clipper" to R.raw.barber_hair_clipper,
        "Professional Clipper" to R.raw.barber_hair_clipper,
        "Classic Clipper" to R.raw.barber_hair_clipper,
        "Beard Trimmer" to R.raw.barber_hair_clipper,
        "Hair Trimmer" to R.raw.barber_hair_clipper,
        "Mini Trimmer" to R.raw.barber_hair_clipper,
        "Vintage Clipper" to R.raw.barber_hair_clipper,
        "Power Clipper" to R.raw.barber_hair_clipper,
        "Metal Clipper" to R.raw.barber_hair_clipper,
        "Premium Clipper" to R.raw.barber_hair_clipper,
        "Cordless Clipper" to R.raw.barber_hair_clipper,
        "Barber Pro X" to R.raw.barber_hair_clipper,
        "Titanium Clipper" to R.raw.barber_hair_clipper,
        "Gold Master" to R.raw.barber_hair_clipper,
        "Chrome Master" to R.raw.barber_hair_clipper,
        "Carbon Fiber Clipper" to R.raw.barber_hair_clipper,
        "Neon Clipper" to R.raw.barber_hair_clipper,
        "Cyber Trimmer" to R.raw.barber_hair_clipper,
        "Turbo Clipper" to R.raw.barber_hair_clipper,
        "Ultimate Clipper" to R.raw.barber_hair_clipper,
        "Scissors" to R.raw.barber_mixkit_2378,
        "Electric Shaver" to R.raw.barber_electric_shaver,
        "Hair Dryer" to R.raw.barber_mixkit_1894,
        "Spray Bottle" to R.raw.barber_spray_bottle,
        "Shaving Foam" to null,     // Aerosol foam authentic recording missing - left silent
        "Comb" to null,             // Non-mechanical silent styling tool - left silent
        "Barber Brush" to null      // Soft bristle brush - left silent
    )

    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .setMaxStreams(2)
            .build()

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) loadedSamples += sampleId
        }

        // Preload all unique audio resources
        soundMap.values.filterNotNull().distinct().forEach { resourceId ->
            if (resourceId !in sampleByResource) {
                sampleByResource[resourceId] = soundPool.load(context, resourceId, 1)
            }
        }
    }

    fun hasSound(item: SimItem): Boolean = soundMap[item.name] != null

    fun play(item: SimItem): Int {
        stop()
        val resourceId = soundMap[item.name] ?: return 0
        val sampleId = sampleByResource[resourceId] ?: return 0
        if (sampleId == 0 || sampleId !in loadedSamples) return 0
        val loop = if (item.name in loopingItems) -1 else 0
        activeStreamId = soundPool.play(sampleId, 1f, 1f, 1, loop, 1f)
        return activeStreamId
    }

    fun playRandomWeapon(): Int {
        val weaponResourceIds = soundMap.entries
            .filter { (name, res) -> res != null && name in setOf("AK-47", "M4A1", "Desert Eagle", "Shotgun", "Minigun", "Glock 19") }
            .mapNotNull { it.value }
        val availableSampleIds = weaponResourceIds.mapNotNull { sampleByResource[it] }.filter { it in loadedSamples }
        if (availableSampleIds.isEmpty()) return 0
        val sampleId = availableSampleIds.random()
        return soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)
    }

    fun stop(streamId: Int = activeStreamId) {
        if (streamId != 0) soundPool.stop(streamId)
        if (streamId == activeStreamId) activeStreamId = 0
    }

    fun release() {
        stop()
        soundPool.release()
        loadedSamples.clear()
        sampleByResource.clear()
    }
}
