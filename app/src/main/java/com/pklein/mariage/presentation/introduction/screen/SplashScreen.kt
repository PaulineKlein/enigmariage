package com.pklein.mariage.presentation.introduction.screen

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.data.model.GameNames
import com.pklein.mariage.data.repository.RoomCheck
import com.pklein.mariage.data.repository.RoomResult
import com.pklein.mariage.presentation.introduction.screen.atome.BoatWithRoom
import com.pklein.mariage.presentation.introduction.screen.atome.NewGameButtons
import com.pklein.mariage.presentation.introduction.screen.atome.RoomChip
import com.pklein.mariage.presentation.introduction.screen.atome.SplashBoat
import com.pklein.mariage.presentation.introduction.screen.atome.SplashButtons
import com.pklein.mariage.presentation.introduction.screen.atome.TrophiesButton
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private val ENTRANCE_STAGGER = 120.milliseconds
private const val ENTRANCE_DURATION_MS = 500
private val ENTRANCE_OFFSET = 24.dp
private const val BOAT_MAX_HEIGHT_RATIO = 0.4f
private val LANDSCAPE_ROW_MAX_WIDTH = 1000.dp
private const val LANDSCAPE_BOAT_WEIGHT = 1.7f

private val LANDSCAPE_TROPHIES_MAX_WIDTH = 360.dp
private const val BUTTONS_WIDTH_RATIO = 0.85f

/** Unlocked and total trophy counts shown on the trophies button. */
data class TrophyProgress(
    val unlocked: Int,
    val total: Int,
)

/** Title, tagline, boat and the three menu buttons, replaces activity_splash.xml (portrait and landscape). */
@Composable
fun SplashScreen(
    hasSavedGame: Boolean,
    roomCode: String?,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
    onCreateRoom: suspend (roomCode: String, names: GameNames) -> RoomResult,
    onJoinRoom: suspend (roomCode: String) -> RoomCheck,
    onMultiplayer: (roomCode: String, names: GameNames, keepProgress: Boolean) -> Unit,
    onLeaveRoom: () -> Unit,
) {
    var showMultiplayer by rememberSaveable { mutableStateOf(false) }
    val onShowMultiplayer = { showMultiplayer = true }

    AppTheme {
        if (showMultiplayer) {
            MultiplayerDialog(
                onCreateRoom = onCreateRoom,
                onJoinRoom = onJoinRoom,
                onStart = { roomCode, names, keepProgress ->
                    showMultiplayer = false
                    onMultiplayer(roomCode, names, keepProgress)
                },
                onDismiss = { showMultiplayer = false },
                hasSavedGame = hasSavedGame,
            )
        }
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
        ) {
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SplashLandscape(
                    hasSavedGame,
                    roomCode,
                    trophyProgress,
                    onNewGame,
                    onContinue,
                    onTrophies,
                    onShowMultiplayer,
                    onLeaveRoom,
                )
            } else {
                SplashPortrait(
                    hasSavedGame,
                    roomCode,
                    trophyProgress,
                    onNewGame,
                    onContinue,
                    onTrophies,
                    onShowMultiplayer,
                    onLeaveRoom,
                )
            }
        }
    }
}

@Composable
private fun SplashPortrait(
    hasSavedGame: Boolean,
    roomCode: String?,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
    onMultiplayer: () -> Unit,
    onLeaveRoom: () -> Unit,
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
            BoatWithRoom(
                roomCode = roomCode,
                hasSavedGame = hasSavedGame,
                onContinue = onContinue,
                onLeaveRoom = onLeaveRoom,
                boatModifier =
                    Modifier
                        .padding(top = 8.dp)
                        .heightIn(max = pageHeight * BOAT_MAX_HEIGHT_RATIO),
                modifier = Modifier.entrance(2),
            )
            SplashButtons(
                trophyProgress = trophyProgress,
                onNewGame = onNewGame,
                onTrophies = onTrophies,
                onMultiplayer = onMultiplayer,
                modifier =
                    Modifier
                        .fillMaxWidth(BUTTONS_WIDTH_RATIO)
                        .entrance(3),
            )
        }
    }
}

/** The whole page scrolls together: the boat, solo and multiplayer side by side on one row, then the room and trophies. */
@Composable
private fun SplashLandscape(
    hasSavedGame: Boolean,
    roomCode: String?,
    trophyProgress: TrophyProgress,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onTrophies: () -> Unit,
    onMultiplayer: () -> Unit,
    onLeaveRoom: () -> Unit,
) {
    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        val pageHeight = maxHeight
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = pageHeight),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SplashTitle(modifier = Modifier.entrance(0))
            SplashTagline(modifier = Modifier.entrance(1))
            NewGameButtons(
                onSolo = onNewGame,
                onMultiplayer = onMultiplayer,
                modifier =
                    Modifier
                        .widthIn(max = LANDSCAPE_ROW_MAX_WIDTH)
                        .height(IntrinsicSize.Min)
                        .entrance(2),
                leading = {
                    // Same height as the cards, wider than them: the square picture is cropped to fit.
                    SplashBoat(
                        modifier =
                            Modifier
                                .weight(LANDSCAPE_BOAT_WEIGHT)
                                .fillMaxHeight(),
                        onResume = if (hasSavedGame) onContinue else null,
                        fillHeight = true,
                    )
                },
            )
            if (roomCode != null) RoomChip(roomCode, onLeaveRoom)
            TrophiesButton(
                trophyProgress = trophyProgress,
                onTrophies = onTrophies,
                modifier =
                    Modifier
                        .widthIn(max = LANDSCAPE_TROPHIES_MAX_WIDTH)
                        .entrance(3),
            )
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
        style = DefaultTextStyles.bodyLink.copy(fontSize = 20.sp),
        textAlign = TextAlign.Center,
        modifier = modifier.padding(horizontal = 16.dp),
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
        roomCode = "K7M2QX",
        trophyProgress = TrophyProgress(unlocked = 3, total = 12),
        onNewGame = {},
        onContinue = {},
        onTrophies = {},
        onCreateRoom = { _, _ -> RoomResult.OK },
        onJoinRoom = { RoomCheck(RoomResult.OK) },
        onMultiplayer = { _, _, _ -> },
        onLeaveRoom = {},
    )
}
