package com.pklein.mariage.presentation.combatFinal.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.modifier.framed
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.ui.theme.gameAnnotatedStringResource
import com.pklein.mariage.ui.theme.gameStringResource

private const val BURST_ANIMATION = "animation_burst.json"
private const val BURST_ITERATIONS = 2
private val BURST_SIZE = 250.dp
private val CONTENT_MAX_WIDTH = 480.dp
private val NOX_FINISH_MAX_WIDTH = 300.dp
private const val VALIDATE_BUTTON_WIDTH_RATIO = 0.7f

/** Nox is defeated: a burst plays twice, then Nox as a doll appears and the results button unlocks. */
@Composable
fun CombatFinalThreeScreen(
    playerName: String,
    onHomeClick: () -> Unit,
    onResultsClick: () -> Unit,
) {
    var animationFinished by rememberSaveable { mutableStateOf(false) }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp),
        ) {
            MyNavigationBar(
                title = R.string.combatFinal_sucess_titre,
                onHomeClick = onHomeClick,
            )

            BoxWithConstraints(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .heightIn(min = maxHeight),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = CONTENT_MAX_WIDTH)
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text =
                                gameAnnotatedStringResource(
                                    R.string.combatFinal_three_message_1,
                                    playerName,
                                ),
                            style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = annotatedStringResource(R.string.combatFinal_three_message_2),
                            style = DefaultTextStyles.body,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    if (animationFinished) {
                        Image(
                            painter = painterResource(R.drawable.image_nox_finish),
                            contentDescription = gameStringResource(R.string.fin_nox_accessibility),
                            contentScale = ContentScale.Fit,
                            modifier =
                                Modifier
                                    .size(NOX_FINISH_MAX_WIDTH)
                                    .framed(Color.Black),
                        )
                    } else {
                        BurstAnimation(onFinished = { animationFinished = true })
                    }
                }
            }

            AppButton(
                text = stringResource(id = R.string.combatFinal_three_validation),
                onClick = onResultsClick,
                enabled = animationFinished,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = CONTENT_MAX_WIDTH)
                        .fillMaxWidth(VALIDATE_BUTTON_WIDTH_RATIO),
            )
        }
    }
}

@Composable
private fun BurstAnimation(onFinished: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(BURST_ANIMATION))
    val animationState = animateLottieCompositionAsState(composition, iterations = BURST_ITERATIONS)

    LaunchedEffect(composition, animationState.isAtEnd) {
        if (composition != null && animationState.isAtEnd) onFinished()
    }

    LottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = Modifier.size(BURST_SIZE),
    )
}

@Preview(showSystemUi = true, name = "Combat final 3")
@Composable
private fun CombatFinalThreeScreenPreview() {
    CombatFinalThreeScreen(
        playerName = "Pauline",
        onHomeClick = {},
        onResultsClick = {},
    )
}

@Preview(showSystemUi = true, name = "Combat final 3 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun CombatFinalThreeScreenLandscapePreview() {
    CombatFinalThreeScreen(
        playerName = "Pauline",
        onHomeClick = {},
        onResultsClick = {},
    )
}
