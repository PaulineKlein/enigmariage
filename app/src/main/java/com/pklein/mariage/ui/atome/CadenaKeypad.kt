package com.pklein.mariage.ui.atome

import android.view.HapticFeedbackConstants
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.ColorDotUnselected
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce
import com.pklein.mariage.ui.theme.GradientStartColor

private val DigitRows =
    listOf(
        listOf(R.string.un, R.string.deux, R.string.trois),
        listOf(R.string.quatre, R.string.cinq, R.string.six),
        listOf(R.string.sept, R.string.huit, R.string.neuf),
    )

private val CARD_CORNER_RADIUS = 24.dp
private const val CARD_BACKGROUND_ALPHA = 0.6f
private val CARD_PADDING = 16.dp
private val DISPLAY_MIN_HEIGHT = 56.dp
private val KEY_WIDTH = 88.dp
private val KEY_HEIGHT = 60.dp
private val KEY_SHAPE = RoundedCornerShape(20.dp)
private val KEY_SPACING = 12.dp
private val KEY_SHADOW_ELEVATION = 4.dp
private val KEY_ICON_SIZE = 28.dp
private const val KEY_TOP_LIGHTEN = 0.5f
private const val KEY_PRESSED_SCALE = 0.9f
private const val DIGIT_POP_START_SCALE = 0.5f
private const val DISABLED_ALPHA = 0.6f

/** The colours of one round key: a soft gradient from a lighter top to [bottom], a tinted shadow, and its content. */
private class KeyColors(
    val bottom: Color,
    val shadow: Color,
    val content: Color,
) {
    val top: Color = lerp(bottom, Color.White, KEY_TOP_LIGHTEN)
}

private val DigitKeyColors = KeyColors(GradientEndColor, GradientEndColorFonce, ColorBleuFonce)
private val DeleteKeyColors = KeyColors(GradientStartColor, ColorDotUnselected, ColorBleuFonce)
private val ValidateKeyColors = KeyColors(ColorHelp, ColorHelpFonce, ColorHelpText)
private val DisabledKeyColors = KeyColors(ColorGrey, ColorGrey, ColorText)

/** A digit pad in a soft card: a pill display, pastel keys, and delete / validate keys with icons. */
@Composable
fun CadenaKeypad(
    value: String,
    onValueChange: (String) -> Unit,
    onValidate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                .background(Color.White.copy(alpha = CARD_BACKGROUND_ALPHA))
                .padding(CARD_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(KEY_SPACING),
    ) {
        CadenaDisplay(value = value, modifier = Modifier.fillMaxWidth())

        DigitRows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(KEY_SPACING)) {
                row.forEach { digitRes ->
                    val digit = stringResource(digitRes)
                    DigitKey(digit = digit, onClick = { onValueChange(value + digit) })
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(KEY_SPACING)) {
            IconKey(
                icon = R.drawable.ic_keypad_backspace,
                description = stringResource(R.string.cadena_delete_accessibility),
                colors = DeleteKeyColors,
                onClick = { onValueChange(value.dropLast(1)) },
            )
            DigitKey(
                digit = stringResource(R.string.zero),
                onClick = { onValueChange(value + "0") },
            )
            val canValidate = value.isNotEmpty()
            IconKey(
                icon = R.drawable.ic_check,
                description = stringResource(R.string.cadena_validate_accessibility),
                colors = if (canValidate) ValidateKeyColors else DisabledKeyColors,
                enabled = canValidate,
                onClick = { onValidate(value) },
            )
        }
    }
}

/** The typed digits on a white rounded square, shaped like the keys; each new digit pops in. */
@Composable
private fun CadenaDisplay(
    value: String,
    modifier: Modifier = Modifier,
) {
    val digitStyle =
        DefaultTextStyles.bodyBig.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = ColorBleuFonce,
            textAlign = TextAlign.Center,
        )
    val shape = KEY_SHAPE

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .heightIn(min = DISPLAY_MIN_HEIGHT)
                .clip(shape)
                .background(Color.White.copy(alpha = 0.9f))
                .border(2.dp, GradientEndColor, shape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        if (value.isEmpty()) {
            Text(
                text = stringResource(R.string.embarquement_one_hint),
                style =
                    DefaultTextStyles.bodyBig.copy(
                        fontSize = 18.sp,
                        color = ColorHint,
                        textAlign = TextAlign.Center,
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.semantics(mergeDescendants = true) {},
            ) {
                // Keyed by position, so only the digit that was just typed pops in.
                value.forEachIndexed { index, digit ->
                    key(index) { PoppingDigit(digit = digit, style = digitStyle) }
                }
            }
        }
    }
}

@Composable
private fun PoppingDigit(
    digit: Char,
    style: TextStyle,
) {
    val scale = remember { Animatable(DIGIT_POP_START_SCALE) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            1f,
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium,
            ),
        )
    }
    Text(
        text = digit.toString(),
        style = style,
        modifier =
            Modifier.graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
    )
}

@Composable
private fun DigitKey(
    digit: String,
    onClick: () -> Unit,
) {
    RoundKey(colors = DigitKeyColors, enabled = true, onClick = onClick) {
        Text(
            text = digit,
            style =
                DefaultTextStyles.body.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = DigitKeyColors.content,
                ),
        )
    }
}

@Composable
private fun IconKey(
    @DrawableRes icon: Int,
    description: String,
    colors: KeyColors,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    RoundKey(
        colors = colors,
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier.semantics { contentDescription = description },
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colors.content,
            modifier = Modifier.size(KEY_ICON_SIZE),
        )
    }
}

/** A softly rounded, softly lit key that squishes and ticks when pressed, like [AppButton]. */
@Composable
private fun RoundKey(
    colors: KeyColors,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val view = LocalView.current
    LaunchedEffect(pressed) {
        if (pressed) view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
    val scale by animateFloatAsState(
        targetValue = if (pressed) KEY_PRESSED_SCALE else 1f,
        animationSpec =
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium,
            ),
        label = "keyScale",
    )
    val keyAlpha = if (enabled) 1f else DISABLED_ALPHA

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .size(KEY_WIDTH, KEY_HEIGHT)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = keyAlpha
                }.then(
                    if (enabled) {
                        Modifier.shadow(
                            KEY_SHADOW_ELEVATION,
                            KEY_SHAPE,
                            ambientColor = colors.shadow,
                            spotColor = colors.shadow,
                        )
                    } else {
                        Modifier
                    },
                ).clip(KEY_SHAPE)
                .background(Brush.verticalGradient(listOf(colors.top, colors.bottom)))
                .border(
                    1.dp,
                    Brush.verticalGradient(
                        listOf(
                            lerp(colors.shadow, Color.White, KEY_TOP_LIGHTEN),
                            colors.shadow,
                        ),
                    ),
                    KEY_SHAPE,
                ).clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
    ) {
        content()
    }
}

@Preview(name = "Vide")
@Composable
private fun CadenaKeypadEmptyPreview() {
    AppTheme {
        CadenaKeypad(
            value = "",
            onValueChange = {},
            onValidate = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Interactif")
@Composable
private fun CadenaKeypadInteractivePreview() {
    var value by remember { mutableStateOf("124") }
    AppTheme {
        CadenaKeypad(
            value = value,
            onValueChange = { value = it },
            onValidate = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
