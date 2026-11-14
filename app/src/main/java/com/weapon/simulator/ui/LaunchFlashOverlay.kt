package com.weapon.simulator.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun LaunchFlashOverlay(pulseId: Int, enabled: Boolean, strength: Float) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(pulseId) {
        if (pulseId == 0) return@LaunchedEffect
        visible = true
        delay(72)
        visible = false
    }

    val alpha by animateFloatAsState(
        targetValue = if (enabled && visible) (0.72f * strength.coerceIn(0.25f, 1f)) else 0f,
        animationSpec = tween(durationMillis = 55),
        label = "launch_flash_alpha"
    )

    if (alpha > 0f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = alpha),
                            Color(0xFFFF163D).copy(alpha = alpha * 0.86f),
                            Color.White.copy(alpha = alpha * 0.72f),
                            Color(0xFFFF163D).copy(alpha = alpha * 0.80f)
                        )
                    )
                )
        )
    }
}
