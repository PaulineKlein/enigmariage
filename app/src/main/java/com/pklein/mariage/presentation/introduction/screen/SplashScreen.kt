package com.pklein.mariage.presentation.introduction.screen

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.modifier.pulse
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private val ENTRANCE_STAGGER = 120.milliseconds
private const val ENTRANCE_DURATION_MS = 500
private val ENTRANCE_OFFSET = 24.dp
private const val BOAT_MAX_HEIGHT_RATIO = 0.4f
private val BOAT_CORNER_RADIUS = 12.dp
private const val BOAT_ZOOM_START_SCALE = 1.25f
private val BOAT_ZOOM_DELAY = 240.milliseconds
private const val BOAT_ZOOM_DURATION_MS = 1400
private const val SPARKLE_FADE_MS = 600
private const val SPARKLE_MAX_ALPHA = 0.9f
private const val SPARKLE_RISE_DP = 8f
private const val BUTTONS_WIDTH_RATIO = 0.7f
private val PRIMARY_TEXT_SIZE = 18.sp
private val PRIMARY_PADDING = 18.dp

/** Unlocked and total trophy counts shown on the trophies button. */
data class TrophyProgress(
    val unlocked: Int,
    val total: Int,
)

/** Title, tagline, boat and the three menu buttons, replaces activity_splash.xml (portrait and landscape). */
@Composable
fun SplashScreen(
    hasSavedGame: Boolean,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
) {
    AppTheme {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
        ) {
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SplashLandscape(hasSavedGame, trophyProgress, onNewGame, onContinue, onTrophies)
            } else {
                SplashPortrait(hasSavedGame, trophyProgress, onNewGame, onContinue, onTrophies)
            }
        }
    }
}

@Composable
private fun SplashPortrait(
    hasSavedGame: Boolean,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
) {
    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        val pageHeight = maxHeight
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = pageHeight),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SplashTitle(modifier = Modifier.entrance(0))
            SplashTagline(modifier = Modifier.entrance(1))
            SplashBoat(
                modifier =
                    Modifier
                        .padding(8.dp)
                        .heightIn(max = pageHeight * BOAT_MAX_HEIGHT_RATIO)
                        .entrance(2),
            )
            SplashButtons(
                hasSavedGame = hasSavedGame,
                trophyProgress = trophyProgress,
                onNewGame = onNewGame,
                onContinue = onContinue,
                onTrophies = onTrophies,
                modifier =
                    Modifier
                        .fillMaxWidth(BUTTONS_WIDTH_RATIO)
                        .entrance(3),
            )
        }
    }
}

@Composable
private fun SplashLandscape(
    hasSavedGame: Boolean,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SplashTitle(modifier = Modifier.entrance(0))
        SplashTagline(modifier = Modifier.entrance(1))
        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .entrance(2),
                contentAlignment = Alignment.Center,
            ) {
                SplashBoat()
            }
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                SplashButtons(
                    hasSavedGame = hasSavedGame,
                    trophyProgress = trophyProgress,
                    onNewGame = onNewGame,
                    onContinue = onContinue,
                    onTrophies = onTrophies,
                    modifier =
                        Modifier
                            .fillMaxWidth(0.9f)
                            .verticalScroll(rememberScrollState())
                            .entrance(3),
                )
            }
        }
    }
}

@Composable
private fun SplashTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.splash_titre),
        style = DefaultTextStyles.titreSplash,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
private fun SplashTagline(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.splash_tagline),
        style = DefaultTextStyles.bodyLink,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(horizontal = 16.dp),
    )
}

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
private fun SplashBoat(modifier: Modifier = Modifier) {
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

    Box(modifier = modifier.aspectRatio(ratio)) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(BOAT_CORNER_RADIUS)),
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.splash_bateau_image),
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = zoom.value
                            scaleY = zoom.value
                        },
            )
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
                }
                .clearAndSetSemantics {},
    )
}

@Composable
private fun SplashButtons(
    hasSavedGame: Boolean,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // The main action (Continuer with a saved game, otherwise Nouvelle partie) comes first, bigger and pulsing.
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (hasSavedGame) {
            PrimaryButton(text = stringResource(R.string.continuer), onClick = onContinue)
            AppButton(
                text = stringResource(R.string.new_game),
                onClick = onNewGame,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            PrimaryButton(text = stringResource(R.string.new_game), onClick = onNewGame)
            AppButton(
                text = stringResource(R.string.continuer),
                onClick = onContinue,
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        AppButton(
            text =
                stringResource(
                    R.string.trophees_progress_button,
                    trophyProgress.unlocked,
                    trophyProgress.total,
                ),
            onClick = onTrophies,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
) {
    AppButton(
        text = text,
        onClick = onClick,
        textSize = PRIMARY_TEXT_SIZE,
        contentPadding = PRIMARY_PADDING,
        modifier =
            Modifier
                .fillMaxWidth()
                .pulse(),
    )
}

/** Fades in and slides up, each element [index] a little after the previous one. */
@Composable
private fun Modifier.entrance(index: Int): Modifier {
    val progress = remember { Animatable(0f) }
    val offsetPx = with(LocalDensity.current) { ENTRANCE_OFFSET.toPx() }

    LaunchedEffect(Unit) {
        delay(ENTRANCE_STAGGER * index)
        progress.animateTo(1f, tween(durationMillis = ENTRANCE_DURATION_MS))
    }

    return graphicsLayer {
        alpha = progress.value
        translationY = (1f - progress.value) * offsetPx
    }
}

@Preview(name = "Splash", showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        hasSavedGame = true,
        trophyProgress = TrophyProgress(unlocked = 3, total = 12),
        onNewGame = {},
        onContinue = {},
        onTrophies = {},
    )
}
