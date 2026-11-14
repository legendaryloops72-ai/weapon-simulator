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
        "Hair Clipper", "Hair Dryer", "Professional Clipper"
    )

    // Exact 1-to-1 audio bindings. Every item is verified by audio content.
    // Items without an authentic matching recording are intentionally set to null (silent)
    // per instructions: "إذا لم تجد تسجيلاً مناسباً لعنصر معين اترك العنصر دون صوت مؤقتاً وأضفه إلى تقرير الأصوات الناقصة"
    val soundMap: Map<String, Int?> = mapOf(
        // --- WEAPONS (23 Items) ---
        // Every weapon sound below is extracted from the supplied video and has a matching catalog image.
        "Glock" to R.raw.weapon_glock,
        "M1911" to R.raw.weapon_m1911,
        "Sig P320" to R.raw.weapon_sig_p320,
        "Desert Eagle" to R.raw.weapon_desert_eagle,
        "Beretta M9" to R.raw.weapon_beretta_m9,
        "Makarov" to R.raw.weapon_makarov,
        "S&W Model 19" to R.raw.weapon_sw_model_19,
        "Browning Citori 725" to R.raw.weapon_browning_citori_725,
        "Remington 870" to R.raw.weapon_remington_870,
        "M26 MASS" to R.raw.weapon_m26_mass,
        "DP-12" to R.raw.weapon_dp12,
        "Origin 12" to R.raw.weapon_origin_12,
        "AA-12" to R.raw.weapon_aa12,
        "Mk 14 EBR" to R.raw.weapon_mk14_ebr,
        "SKS" to R.raw.weapon_sks,
        "FN FAL" to R.raw.weapon_fn_fal,
        "Dragunov" to R.raw.weapon_dragunov,
        "Barrett" to R.raw.weapon_barrett,
        "Marlin Model 336" to R.raw.weapon_marlin_model_336,
        "Kar 98k" to R.raw.weapon_kar_98k,
        "R700" to R.raw.weapon_r700,
        "HDR" to R.raw.weapon_hdr,
        "AX50" to R.raw.weapon_ax50,

        // --- BARBER TOOLS (5 Items) ---
        "Hair Clipper" to R.raw.barber_hair_clipper,
        "Scissors" to R.raw.barber_scissors,
        "Hair Dryer" to R.raw.barber_hair_dryer,
        "Spray Bottle" to R.raw.barber_spray_bottle,
        "Professional Clipper" to R.raw.barber_professional_clipper
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
            .filter { (name, res) -> res != null && name in setOf("Glock", "M1911", "Sig P320", "Desert Eagle", "Beretta M9", "Makarov", "S&W Model 19", "Browning Citori 725", "Remington 870", "M26 MASS", "DP-12", "Origin 12", "AA-12", "Mk 14 EBR", "SKS", "FN FAL", "Dragunov", "Barrett", "Marlin Model 336", "Kar 98k", "R700", "HDR", "AX50") }
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
