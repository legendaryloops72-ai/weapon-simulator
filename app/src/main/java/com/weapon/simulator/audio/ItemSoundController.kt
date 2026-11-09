package com.weapon.simulator.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.weapon.simulator.R
import com.weapon.simulator.model.Section
import com.weapon.simulator.model.SimItem

class ItemSoundController(context: Context, catalog: List<SimItem>) {
    private val soundPool: SoundPool
    private val sampleByItem = mutableMapOf<String, Int>()
    private val sampleByResource = mutableMapOf<Int, Int>()
    private val loadedSamples = mutableSetOf<Int>()
    private var activeStreamId = 0

    private val loopingItems = setOf(
        "Hair Clipper", "Professional Clipper", "Classic Clipper", "Beard Trimmer", "Hair Trimmer",
        "Mini Trimmer", "Vintage Clipper", "Power Clipper", "Metal Clipper", "Premium Clipper",
        "Cordless Clipper", "Barber Pro X", "Titanium Clipper", "Gold Master", "Chrome Master",
        "Carbon Fiber Clipper", "Neon Clipper", "Cyber Trimmer", "Turbo Clipper", "Ultimate Clipper",
        "Electric Shaver", "Hair Dryer", "Ice Scepter", "Plasma Staff", "Dragon Staff", "Electric Fan",
        "Cosmic Staff", "Energy Vortex"
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

        resourcesBySection.values.flatten().distinct().forEach { resourceId ->
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
        val loop = if (item.name in loopingItems) -1 else 0
        activeStreamId = soundPool.play(sampleId, 1f, 1f, 1, loop, 1f)
        return activeStreamId
    }

    fun playRandomWeapon(): Int {
        val sampleList = loadedSamples.toList()
        if (sampleList.isEmpty()) return 0
        val sampleId = sampleList.random()
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
    }
}
