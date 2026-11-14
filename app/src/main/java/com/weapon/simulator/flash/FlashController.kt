package com.weapon.simulator.flash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat

/** Controls the rear torch defensively; unsupported devices simply no-op. */
class FlashController(context: Context) {
    private val appContext = context.applicationContext
    private val cameraManager = appContext.getSystemService(Context.CAMERA_SERVICE) as? CameraManager
    private val handler = Handler(Looper.getMainLooper())
    private var offTask: Runnable? = null
    private var torchOn = false

    private val torchCameraId: String? = try {
        cameraManager?.cameraIdList?.firstOrNull { id ->
            val c = cameraManager.getCameraCharacteristics(id)
            val hasFlash = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            val isBack = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
            hasFlash && isBack
        }
    } catch (_: Exception) {
        null
    }

    val isSupported: Boolean
        get() = cameraManager != null && torchCameraId != null

    fun pulse(enabled: Boolean, durationMs: Long = 70L) {
        if (!enabled || !isSupported || !hasCameraPermission()) return
        val id = torchCameraId ?: return
        handler.removeCallbacksAndMessages(null)
        try {
            cameraManager?.setTorchMode(id, true)
            torchOn = true
            offTask = Runnable { setTorch(false) }.also { handler.postDelayed(it, durationMs) }
        } catch (_: SecurityException) {
            torchOn = false
        } catch (_: Exception) {
            torchOn = false
        }
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
        setTorch(false)
    }

    fun release() {
        stop()
        offTask = null
    }

    private fun setTorch(enabled: Boolean) {
        val id = torchCameraId ?: return
        if (!hasCameraPermission()) {
            torchOn = false
            return
        }
        try {
            cameraManager?.setTorchMode(id, enabled)
            torchOn = enabled
        } catch (_: SecurityException) {
            torchOn = false
        } catch (_: Exception) {
            torchOn = false
        }
    }

    private fun hasCameraPermission(): Boolean =
        ContextCompat.checkSelfPermission(appContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}
