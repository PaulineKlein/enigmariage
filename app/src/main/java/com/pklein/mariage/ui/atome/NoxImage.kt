package com.pklein.mariage.ui.atome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

private const val NOX_HINT_FADE_MS = 800
private const val NOX_HINT_PULSE_MS = 1400
private const val NOX_HINT_MIN_ALPHA = 0.15f
private const val NOX_HINT_MAX_ALPHA = 0.6f
private const val NOX_FLOAT_DP = 8f
private const val NOX_FLOAT_DURATION_MS = 1800
private const val NOX_BUILD_UP_SCALE = 0.08f
private const val NOX_WIGGLE_BASE_DEGREES = 3f
private const val NOX_WIGGLE_STEP_DEGREES = 2f
private val NOX_HINT_DELAY = 6.seconds

/** Floating Nox: each tap wiggles and swells him a bit more, and a faint "?" nudges idle players to tap. */
@Composable
fun NoxImage(
    tapCount: Int,
    maxTapCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val floatTransition = rememberInfiniteTransition(label = "noxFloat")
    val floatOffset by floatTransition.animateFloat(
        initialValue = 0f,
        targetValue = -NOX_FLOAT_DP,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = NOX_FLOAT_DURATION_MS,
                        easing = FastOutSlowInEasing,
                    ),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "noxFloatOffset",
    )

    // Each tap wiggles Nox harder and makes him swell a bit more, so the player feels something building.
    val progress = tapCount.coerceAtMost(maxTapCount)
    val buildUpScale by animateFloatAsState(
        targetValue = 1f + NOX_BUILD_UP_SCALE * progress / maxTapCount,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "noxBuildUpScale",
    )
    val wiggle = remember { Animatable(0f) }
    val initialTapCount = remember { tapCount }
    LaunchedEffect(tapCount) {
        if (tapCount > initialTapCount) {
            val amplitude = NOX_WIGGLE_BASE_DEGREES + NOX_WIGGLE_STEP_DEGREES * progress
            wiggle.animateTo(amplitude, tween(durationMillis = 60))
            wiggle.animateTo(-amplitude, tween(durationMillis = 120))
            wiggle.animateTo(amplitude / 2, tween(durationMillis = 100))
            wiggle.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
        }
    }

    // A faint "?" shows up when the player stays idle, nudging them to tap Nox; it goes away on the next tap.
    var showHint by remember { mutableStateOf(false) }
    LaunchedEffect(tapCount) {
        showHint = false
        if (tapCount < maxTapCount) {
            delay(NOX_HINT_DELAY)
            showHint = true
        }
    }

    val painter = painterResource(R.drawable.image_nox)
    val ratio = painter.intrinsicSize.run { width / height }

    Box(
        modifier =
            modifier
                .aspectRatio(ratio)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ).graphicsLayer {
                    translationY = floatOffset.dp.toPx()
                    rotationZ = wiggle.value
                    scaleX = buildUpScale
                    scaleY = buildUpScale
                },
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.introduction_nox_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
        AnimatedVisibility(
            visible = showHint,
            enter = fadeIn(animationSpec = tween(durationMillis = NOX_HINT_FADE_MS)),
            exit = fadeOut(),
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp),
        ) {
            NoxHint()
        }
    }
}

@Composable
private fun NoxHint() {
    val transition = rememberInfiniteTransition(label = "noxHint")
    val hintAlpha by transition.animateFloat(
        initialValue = NOX_HINT_MIN_ALPHA,
        targetValue = NOX_HINT_MAX_ALPHA,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = NOX_HINT_PULSE_MS, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "noxHintAlpha",
    )

    Text(
        text = "?",
        style = DefaultTextStyles.titre,
        modifier =
            Modifier
                .graphicsLayer { alpha = hintAlpha }
                .clearAndSetSemantics {},
    )
}

@Preview(showBackground = true, name = "Nox")
@Composable
private fun NoxImagePreview() {
    AppTheme {
        NoxImage(tapCount = 2, maxTapCount = 5, onClick = {}, modifier = Modifier.padding(16.dp))
    }
}
