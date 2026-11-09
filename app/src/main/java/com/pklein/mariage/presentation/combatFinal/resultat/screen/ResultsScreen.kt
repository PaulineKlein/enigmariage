package com.pklein.mariage.presentation.combatFinal.resultat.screen

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorPrimaryDark
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.annotatedStringResource
import kotlin.math.roundToInt

private val SCREEN_PADDING = 16.dp
private val SECTION_SPACING = 16.dp
private val HERO_CORNER_RADIUS = 24.dp
private val HERO_PADDING = 16.dp
private val CUP_SIZE = 90.dp
private const val HERO_BACKGROUND_ALPHA = 0.6f
private val SCORE_FONT_SIZE = 72.sp
private const val SCORE_COUNT_MS = 1500
private val CHIP_CORNER_RADIUS = 16.dp
private const val CHIP_BACKGROUND_ALPHA = 0.6f
private val CHIP_ICON_SIZE = 22.dp
private val FRAME_CORNER_RADIUS = 16.dp
private val FRAME_SHADOW = 6.dp
private val FRAME_BORDER = 3.dp
private val THANKS_IMAGE_SIZE = 200.dp
private val SHARE_CARD_MAX_WIDTH = 360.dp
private const val SHARE_BUTTON_WIDTH_RATIO = 0.8f
private const val CUP_ANIMATION = "animation_cup.json"
private const val SCORE_LEGEND = 100
private const val SCORE_EXCELLENT = 80
private const val SCORE_GOOD = 50

/**
 * Final results: a hero card (cup, rank, big animated score, time and trophies chips), the wedding thank-you picture,
 * a preview of the card that will be shared, and the share / back-to-home actions. Replaces fragment_results.xml.
 */
@Composable
fun ResultsScreen(
    score: Int,
    time: String,
    trophies: String,
    shareCard: ImageBitmap?,
    onHomeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    AppTheme {
        // Scrollable so small screens and big fonts never clip, yet the actions stay at the bottom when there is room.
        BoxWithConstraints(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
        ) {
            val availableHeight = maxHeight
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = availableHeight)
                        .padding(SCREEN_PADDING),
                verticalArrangement = Arrangement.spacedBy(SECTION_SPACING),
            ) {
                MyNavigationBar(title = R.string.resultat_titre, onHomeClick = onHomeClick)

                ResultsHero(score = score, time = time, trophies = trophies)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(SECTION_SPACING),
                ) {
                    Text(
                        text = annotatedStringResource(R.string.resultat_message_titre),
                        style = DefaultTextStyles.sousTitre,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Image(
                        painter = painterResource(R.drawable.image_merci),
                        contentDescription = stringResource(R.string.fin_mariage_accessibility),
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .size(THANKS_IMAGE_SIZE)
                                .framed(),
                    )
                    Text(
                        text = annotatedStringResource(R.string.resultat_message),
                        style = DefaultTextStyles.body,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (shareCard != null) {
                        Image(
                            bitmap = shareCard,
                            contentDescription = stringResource(R.string.resultat_share_card_accessibility),
                            contentScale = ContentScale.FillWidth,
                            modifier =
                                Modifier
                                    .widthIn(max = SHARE_CARD_MAX_WIDTH)
                                    .fillMaxWidth()
                                    .framed(),
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AppButton(
                        text = stringResource(R.string.resultat_share),
                        onClick = onShareClick,
                        modifier = Modifier.fillMaxWidth(SHARE_BUTTON_WIDTH_RATIO),
                    )
                    TextButton(onClick = onRestartClick) {
                        Text(
                            text = stringResource(R.string.resultat_bouton),
                            style = DefaultTextStyles.bodyLink,
                        )
                    }
                }
            }
        }
    }
}

/** Cup, rank and the score counting up from 0, then the time and trophies as two chips. */
@Composable
private fun ResultsHero(
    score: Int,
    time: String,
    trophies: String,
) {
    val shownScore = remember { Animatable(0f) }
    LaunchedEffect(score) {
        shownScore.animateTo(score.toFloat(), tween(SCORE_COUNT_MS, easing = FastOutSlowInEasing))
    }
    val scoreDescription =
        stringResource(rankFor(score)) + ", " + stringResource(R.string.resultat_score_value, score)
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(CUP_ANIMATION))

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(HERO_CORNER_RADIUS))
                .background(Color.White.copy(alpha = HERO_BACKGROUND_ALPHA))
                .padding(HERO_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SECTION_SPACING / 2),
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(CUP_SIZE),
        )

        // The final value is announced at once, not each number of the count-up.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clearAndSetSemantics { contentDescription = scoreDescription },
        ) {
            Text(
                text = stringResource(rankFor(score)),
                style = DefaultTextStyles.sousTitre.copy(color = ColorPrimaryDark),
                textAlign = TextAlign.Center,
            )
            Row {
                Text(
                    text = shownScore.value.roundToInt().toString(),
                    style = DefaultTextStyles.titre.copy(fontSize = SCORE_FONT_SIZE),
                    modifier = Modifier.alignByBaseline(),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.resultat_score_max),
                    style = DefaultTextStyles.bodyBig.copy(color = ColorHint),
                    modifier = Modifier.alignByBaseline(),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatChip(
                icon = R.drawable.ic_clock,
                value = time,
                label = R.string.resultat_temps,
                modifier = Modifier.weight(1f),
            )
            StatChip(
                icon = R.drawable.ic_trophy,
                value = trophies,
                label = R.string.resultat_trophees,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StatChip(
    icon: Int,
    value: String,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
) {
    val description = stringResource(label) + " " + value

    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(CHIP_CORNER_RADIUS))
                .background(GradientEndColor.copy(alpha = CHIP_BACKGROUND_ALPHA))
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clearAndSetSemantics { contentDescription = description },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(CHIP_ICON_SIZE),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, style = DefaultTextStyles.bodyBig, textAlign = TextAlign.Center)
    }
}

@StringRes
private fun rankFor(score: Int): Int =
    when {
        score >= SCORE_LEGEND -> R.string.resultat_rank_legend
        score >= SCORE_EXCELLENT -> R.string.resultat_rank_excellent
        score >= SCORE_GOOD -> R.string.resultat_rank_good
        else -> R.string.resultat_rank_rookie
    }

/** Rounded picture with a soft shadow and a thin white border, like a printed photo. */
private fun Modifier.framed(): Modifier {
    val shape = RoundedCornerShape(FRAME_CORNER_RADIUS)
    return this
        .shadow(FRAME_SHADOW, shape)
        .clip(shape)
        .border(FRAME_BORDER, Color.White, shape)
}

@Preview(name = "Résultats", showSystemUi = true)
@Composable
private fun ResultsScreenPreview() {
    ResultsScreen(
        score = 87,
        time = "42 min 10 s",
        trophies = "12 / 40",
        shareCard = ImageBitmap(425, 225),
        onHomeClick = {},
        onRestartClick = {},
        onShareClick = {},
    )
}
