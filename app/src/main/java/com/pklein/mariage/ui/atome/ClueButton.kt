package com.pklein.mariage.ui.atome

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.DefaultTextStyles

/** Compose equivalent of layout/indice.xml, styled like [AppButton]: soft gradient, tinted shadow, tactile press. */
@Composable
fun ClueButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val view = LocalView.current
    LaunchedEffect(pressed) {
        if (pressed) view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

    val pressedFraction by animateFloatAsState(
        targetValue = if (pressed) 1f else 0f,
        animationSpec = tween(durationMillis = PRESS_ANIMATION_MS),
        label = "clueButtonPressed",
    )
    // Lighter at the top, the original green at the bottom; both darken a little while pressed.
    val darken = pressedFraction * PRESSED_DARKEN
    val fill =
        Brush.verticalGradient(
            listOf(
                lerp(lerp(ColorHelp, Color.White, TOP_LIGHTEN), ColorHelpFonce, darken),
                lerp(ColorHelp, ColorHelpFonce, darken),
            ),
        )
    // A lighter top edge gives a subtle highlight.
    val border =
        BorderStroke(
            1.dp,
            Brush.verticalGradient(
                listOf(
                    lerp(ColorHelpFonce, Color.White, TOP_LIGHTEN),
                    ColorHelpFonce,
                ),
            ),
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .height(48.dp)
                .graphicsLayer {
                    val scale = 1f - (1f - PRESSED_SCALE) * pressedFraction
                    scaleX = scale
                    scaleY = scale
                }.shadow(
                    elevation = BUTTON_SHADOW_ELEVATION,
                    shape = shape,
                    ambientColor = ColorHelpFonce,
                    spotColor = ColorHelpFonce,
                ).clip(shape)
                .background(fill)
                .border(border, shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    role = Role.Button,
                    onClick = onClick,
                ).padding(horizontal = 13.dp),
    ) {
        Text(
            text = text,
            style =
                DefaultTextStyles.body.copy(
                    fontSize = 16.sp,
                    color = ColorHelpText,
                    letterSpacing = LETTER_SPACING,
                ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 8.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorHelpText),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Preview(name = "Indice")
@Composable
private fun ClueButtonPreview() {
    AppTheme {
        ClueButton(
            text = "Indice",
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
