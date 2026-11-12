package com.pklein.mariage.ui.modifier

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private const val DEFAULT_PULSE_SCALE = 1.03f
private const val DEFAULT_PULSE_DURATION_MS = 900

/** Gentle, endless grow-and-shrink used to draw the eye to the main action. */
@Composable
fun Modifier.pulse(
    maxScale: Float = DEFAULT_PULSE_SCALE,
    durationMillis: Int = DEFAULT_PULSE_DURATION_MS,
): Modifier {
    val transition = rememberInfiniteTransition(label = "pulse")
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = maxScale,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "pulseScale",
    )

    return graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}
