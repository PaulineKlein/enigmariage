package com.pklein.mariage.ui.atome

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce

private val BUTTON_SHAPE = RoundedCornerShape(12.dp)
internal val BUTTON_SHADOW_ELEVATION = 3.dp
internal const val TOP_LIGHTEN = 0.45f
internal const val PRESSED_DARKEN = 0.2f
internal const val PRESSED_SCALE = 0.97f
internal const val PRESS_ANIMATION_MS = 90
internal val LETTER_SPACING = 0.4.sp

private class ButtonStyle(
    val fill: Brush,
    val border: BorderStroke?,
    val textColor: Color,
    val hasShadow: Boolean,
)

private fun buttonStyle(
    enabled: Boolean,
    pressedFraction: Float,
): ButtonStyle =
    if (enabled) {
        // Slightly lighter at the top, the original blue at the bottom; both darken a little while pressed.
        val darken = pressedFraction * PRESSED_DARKEN
        ButtonStyle(
            fill =
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
            // A lighter top edge gives a subtle highlight.
            border =
                BorderStroke(
                    1.dp,
                    Brush.verticalGradient(
                        listOf(
                            lerp(
                                GradientEndColorFonce,
                                Color.White,
                                TOP_LIGHTEN,
                            ),
                            GradientEndColorFonce,
                        ),
                    ),
                ),
            textColor = ColorBleuFonce,
            hasShadow = true,
        )
    } else {
        // A disabled button is a plain grey button: unmistakably unavailable.
        ButtonStyle(
            fill = SolidColor(ColorGrey),
            border = BorderStroke(1.dp, ColorGrey),
            textColor = ColorText,
            hasShadow = false,
        )
    }

/** Compose equivalent of style="@style/button", with a soft gradient, a tinted shadow and a tactile press. */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textSize: TextUnit = 14.sp,
    contentPadding: Dp = 13.dp,
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
        label = "buttonPressed",
    )
    val style = buttonStyle(enabled, pressedFraction)

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier =
            modifier
                .heightIn(min = 48.dp)
                .graphicsLayer {
                    val scale = 1f - (1f - PRESSED_SCALE) * pressedFraction
                    scaleX = scale
                    scaleY = scale
                }
                .then(
                    if (style.hasShadow) {
                        Modifier.shadow(
                            elevation = BUTTON_SHADOW_ELEVATION,
                            shape = BUTTON_SHAPE,
                            ambientColor = GradientEndColorFonce,
                            spotColor = GradientEndColorFonce,
                        )
                    } else {
                        Modifier
                    },
                )
                .background(style.fill, BUTTON_SHAPE),
        shape = BUTTON_SHAPE,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = style.textColor,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = style.textColor,
            ),
        border = style.border,
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(contentPadding),
    ) {
        Text(
            text = text.uppercase(Locale.current.platformLocale),
            style =
                DefaultTextStyles.body.copy(
                    fontSize = textSize,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = LETTER_SPACING,
                    color = style.textColor,
                ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AppButtonPreviewColumn(content: @Composable () -> Unit) {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            content()
        }
    }
}

@Preview(name = "Activé / désactivé", widthDp = 360)
@Composable
private fun AppButtonStatesPreview() {
    AppButtonPreviewColumn {
        AppButton(text = "Activé", onClick = {}, modifier = Modifier.fillMaxWidth())
        AppButton(text = "Désactivé", onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(name = "Tailles (splash)", widthDp = 360)
@Composable
private fun AppButtonSizesPreview() {
    AppButtonPreviewColumn {
        AppButton(
            text = "Continuer",
            onClick = {},
            textSize = 18.sp,
            contentPadding = 18.dp,
            modifier = Modifier.fillMaxWidth(),
        )
        AppButton(text = "Nouvelle partie", onClick = {}, modifier = Modifier.fillMaxWidth())
        AppButton(
            text = "Mes Trophées 3/40 \uD83C\uDFC6",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(name = "Texte long", widthDp = 240)
@Composable
private fun AppButtonLongTextPreview() {
    AppButtonPreviewColumn {
        AppButton(
            text = "Un libellé vraiment très long",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
