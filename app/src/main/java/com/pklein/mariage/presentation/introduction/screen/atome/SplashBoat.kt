package com.pklein.mariage.presentation.introduction.screen.atome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.presentation.introduction.screen.TrophyProgress
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// Landscape on a narrower screen gives the boat a small picture: its button label would not fit on one line.
private const val COMPACT_OVERLAY_SCREEN_WIDTH_DP = 700
private val BOAT_CORNER_RADIUS = 12.dp
private const val BOAT_ZOOM_START_SCALE = 1.25f
private val BOAT_ZOOM_DELAY = 240.milliseconds
private const val BOAT_ZOOM_DURATION_MS = 1400
private const val SPARKLE_FADE_MS = 600
private const val SPARKLE_MAX_ALPHA = 0.9f
private const val SPARKLE_RISE_DP = 8f

private class SparkleSpec(
    val alignment: Alignment,
    val offsetX: Dp,
    val offsetY: Dp,
    val stagger: Duration,
)

private val SPARKLES =
    listOf(
        SparkleSpec(Alignment.TopStart, offsetX = 16.dp, offsetY = 12.dp, stagger = 0.milliseconds),
        SparkleSpec(
            Alignment.TopEnd,
            offsetX = (-20).dp,
            offsetY = 28.dp,
            stagger = 300.milliseconds,
        ),
        SparkleSpec(
            Alignment.CenterEnd,
            offsetX = (-8).dp,
            offsetY = 40.dp,
            stagger = 600.milliseconds,
        ),
    )

/** Pulls back once from a slight zoom when the screen opens, then stays still; sized to the picture so the corners hug it. */
@Composable
fun SplashBoat(
    modifier: Modifier = Modifier,
    onResume: (() -> Unit)? = null,
    // Fills the height it is given (cropping the picture) instead of keeping the picture's proportions.
    fillHeight: Boolean = false,
) {
    val painter = painterResource(R.drawable.image_bateau_mariage)
    val ratio = painter.intrinsicSize.run { width / height }

    val zoom = remember { Animatable(BOAT_ZOOM_START_SCALE) }
    LaunchedEffect(Unit) {
        delay(BOAT_ZOOM_DELAY)
        zoom.animateTo(
            1f,
            tween(durationMillis = BOAT_ZOOM_DURATION_MS, easing = FastOutSlowInEasing),
        )
    }

    // With a saved game the whole picture is the "Reprendre" button (a button overlay on its bottom); otherwise decor.
    val resumeModifier =
        if (onResume != null) {
            Modifier
                .clip(RoundedCornerShape(BOAT_CORNER_RADIUS))
                .clickable(role = Role.Button, onClick = onResume)
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(if (fillHeight) Modifier else Modifier.aspectRatio(ratio))
                .then(resumeModifier),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(BOAT_CORNER_RADIUS)),
        ) {
            // Painted without its own size, so the picture never decides the height of the row it sits in.
            val description = stringResource(R.string.splash_bateau_image)
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = zoom.value
                            scaleY = zoom.value
                        }.semantics {
                            contentDescription = description
                            role = Role.Image
                        }.paint(painter, contentScale = ContentScale.Crop, sizeToIntrinsics = false),
            )
            if (onResume != null) {
                ResumeButtonOverlay(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    compact = fillHeight && LocalConfiguration.current.screenWidthDp < COMPACT_OVERLAY_SCREEN_WIDTH_DP,
                )
            }
        }
        SPARKLES.forEach { sparkle ->
            Sparkle(
                spec = sparkle,
                modifier =
                    Modifier
                        .align(sparkle.alignment)
                        .offset(sparkle.offsetX, sparkle.offsetY),
            )
        }
    }
}

/** The boat (the "Reprendre" button when there is a saved game) with the room of the saved game just below. */
@Composable
fun BoatWithRoom(
    roomCode: String?,
    hasSavedGame: Boolean,
    onContinue: () -> Unit,
    onLeaveRoom: () -> Unit,
    boatModifier: Modifier,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SplashBoat(modifier = boatModifier, onResume = if (hasSavedGame) onContinue else null)
        if (roomCode != null) RoomChip(roomCode, onLeaveRoom)
    }
}

/** A "✨" that fades in once, drifting into place after the zoom, then stays still. */
@Composable
private fun Sparkle(
    spec: SparkleSpec,
    modifier: Modifier = Modifier,
) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(BOAT_ZOOM_DELAY + BOAT_ZOOM_DURATION_MS.milliseconds + spec.stagger)
        progress.animateTo(
            1f,
            tween(durationMillis = SPARKLE_FADE_MS, easing = FastOutSlowInEasing),
        )
    }

    Text(
        text = "✨",
        style = DefaultTextStyles.body.copy(fontSize = 18.sp),
        modifier =
            modifier
                .graphicsLayer {
                    alpha = progress.value * SPARKLE_MAX_ALPHA
                    translationY = (1f - progress.value) * SPARKLE_RISE_DP.dp.toPx()
                }.clearAndSetSemantics {},
    )
}

@Composable
fun SplashButtons(
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onTrophies: () -> Unit,
    onMultiplayer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NewGameButtons(
            onSolo = onNewGame,
            onMultiplayer = onMultiplayer,
        )
        TrophiesButton(trophyProgress, onTrophies)
    }
}
