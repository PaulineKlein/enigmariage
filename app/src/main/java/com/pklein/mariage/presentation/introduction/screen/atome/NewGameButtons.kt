package com.pklein.mariage.presentation.introduction.screen.atome

import android.view.HapticFeedbackConstants
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.BUTTON_SHADOW_ELEVATION
import com.pklein.mariage.ui.atome.LETTER_SPACING
import com.pklein.mariage.ui.atome.PRESSED_DARKEN
import com.pklein.mariage.ui.atome.PRESSED_SCALE
import com.pklein.mariage.ui.atome.PRESS_ANIMATION_MS
import com.pklein.mariage.ui.atome.TOP_LIGHTEN
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce

private val CARD_SHAPE = RoundedCornerShape(16.dp)
private val CARD_PADDING = 10.dp
private const val LABEL_LINES = 2

/**
 * A big tappable card: an illustration over a label, in the look of [com.pklein.mariage.ui.atome.AppButton] (soft gradient, tinted shadow,
 * tactile press). The illustration is decorative, the label is what a screen reader announces.
 */
@Composable
private fun GameModeCard(
    text: String,
    @DrawableRes image: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val view = LocalView.current
    LaunchedEffect(pressed) {
        if (pressed) view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
    val pressedFraction by animateFloatAsState(
        targetValue = if (pressed) 1f else 0f,
        animationSpec = tween(durationMillis = PRESS_ANIMATION_MS),
        label = "cardPressed",
    )
    val darken = pressedFraction * PRESSED_DARKEN

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier =
            modifier
                .graphicsLayer {
                    val scale = 1f - (1f - PRESSED_SCALE) * pressedFraction
                    scaleX = scale
                    scaleY = scale
                }
                .shadow(
                    elevation = BUTTON_SHADOW_ELEVATION,
                    shape = CARD_SHAPE,
                    ambientColor = GradientEndColorFonce,
                    spotColor = GradientEndColorFonce,
                )
                .background(
                    Brush.verticalGradient(
                        listOf(
                            lerp(
                                lerp(GradientEndColor, Color.White, TOP_LIGHTEN),
                                GradientEndColorFonce,
                                darken,
                            ),
                            lerp(GradientEndColor, GradientEndColorFonce, darken),
                        ),
                    ),
                    CARD_SHAPE,
                )
                .border(
                    BorderStroke(1.dp, GradientEndColorFonce),
                    CARD_SHAPE,
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .padding(CARD_PADDING),
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )
        Text(
            text = text.uppercase(Locale.current.platformLocale),
            style =
                DefaultTextStyles.body.copy(
                    fontWeight = FontWeight.Normal,
                    letterSpacing = LETTER_SPACING,
                    color = ColorBleuFonce,
                ),
            textAlign = TextAlign.Center,
            minLines = LABEL_LINES,
            maxLines = LABEL_LINES,
        )
    }
}

/**
 * Both ways of starting a new game, as two illustrated cards side by side. [leading] adds an item before them in
 * the same row (give it `Modifier.weight(1f)` to size it like a card).
 */
@Composable
fun NewGameButtons(
    onSolo: () -> Unit,
    onMultiplayer: () -> Unit,
    modifier: Modifier = Modifier,
    leading: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leading()
        GameModeCard(
            text = stringResource(R.string.splash_solo),
            image = R.drawable.image_button_start_one,
            onClick = onSolo,
            modifier = Modifier.weight(1f),
        )
        GameModeCard(
            text = stringResource(R.string.splash_multiplayer),
            image = R.drawable.image_button_start_many,
            onClick = onMultiplayer,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(name = "Nouvelle partie", widthDp = 360)
@Composable
private fun NewGameButtonsPreview() {
    AppTheme {
        NewGameButtons(
            onSolo = {},
            onMultiplayer = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
