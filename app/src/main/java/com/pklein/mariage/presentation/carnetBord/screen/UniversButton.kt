package com.pklein.mariage.presentation.carnetBord.screen

import android.view.HapticFeedbackConstants
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.withResumed
import com.pklein.mariage.R
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.ui.atome.BUTTON_SHADOW_ELEVATION
import com.pklein.mariage.ui.atome.LETTER_SPACING
import com.pklein.mariage.ui.atome.PRESSED_DARKEN
import com.pklein.mariage.ui.atome.PRESSED_SCALE
import com.pklein.mariage.ui.atome.PRESS_ANIMATION_MS
import com.pklein.mariage.ui.atome.TOP_LIGHTEN
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.ColorGrey
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.ColorSuccess
import com.pklein.mariage.ui.theme.ColorSuccessDark
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce
import kotlinx.coroutines.delay

private val BUTTON_SHAPE = RoundedCornerShape(12.dp)
private val IMAGE_SIZE_HORIZONTAL = 100.dp
private val IMAGE_MAX_WIDTH_VERTICAL = 140.dp
private val IMAGE_CORNER_RADIUS = 8.dp
private val BUTTON_CONTENT_PADDING = 8.dp
private val CONTENT_SPACING = 16.dp
private const val LOCKED_IMAGE_ALPHA = 0.55f
private val BADGE_SIZE = 36.dp
private val BADGE_OVERHANG = 8.dp
private const val BADGE_ICON_RATIO = 0.6f
private const val CHECK_POP_DELAY_MS = 450L
private val GRAYSCALE = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

/** What a universe card shows and where it leads, [step] being the name of the [com.pklein.mariage.presentation.GameStep] to launch. A null [image] makes a text-only button. */
data class UniversCardState(
    @DrawableRes val image: Int?,
    @StringRes val nameRes: Int,
    val status: UniversStatus,
    val clickable: Boolean,
    val step: String,
    val id: String,
    val animateCheck: Boolean = false,
)

/** How the button looks for one status; a null [shadowColor] means no shadow. */
private class UniversButtonStyle(
    val fill: Brush,
    val border: BorderStroke,
    val textColor: Color,
    val shadowColor: Color?,
)

private fun universButtonStyle(
    status: UniversStatus,
    pressedFraction: Float,
): UniversButtonStyle =
    when (status) {
        UniversStatus.NOT_STARTED -> {
            UniversButtonStyle(
                SolidColor(ColorGrey),
                BorderStroke(1.dp, ColorGrey),
                ColorText,
                null,
            )
        }

        UniversStatus.GOING -> {
            gradientStyle(GradientEndColor, GradientEndColorFonce, ColorBleuFonce, pressedFraction)
        }

        UniversStatus.IS_FINISHED -> {
            gradientStyle(ColorHelp, ColorHelpFonce, ColorHelpText, pressedFraction)
        }
    }

// Slightly lighter at the top, [base] at the bottom; both darken a little while pressed.
private fun gradientStyle(
    base: Color,
    dark: Color,
    textColor: Color,
    pressedFraction: Float,
): UniversButtonStyle {
    val darken = pressedFraction * PRESSED_DARKEN
    return UniversButtonStyle(
        fill =
            Brush.verticalGradient(
                listOf(
                    lerp(lerp(base, Color.White, TOP_LIGHTEN), dark, darken),
                    lerp(base, dark, darken),
                ),
            ),
        border =
            BorderStroke(
                1.dp,
                Brush.verticalGradient(listOf(lerp(dark, Color.White, TOP_LIGHTEN), dark)),
            ),
        textColor = textColor,
        shadowColor = dark,
    )
}

/**
 * A universe as one button holding its picture and its name.
 * Not started: grey and picture greyed. Going: blue. Finished: green with a check badge on its corner.
 * [horizontal] puts the picture beside the name (portrait), otherwise above it (landscape).
 */
@Composable
fun UniversButton(
    state: UniversCardState,
    onClick: () -> Unit,
    horizontal: Boolean,
    onCheckShown: () -> Unit,
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
        label = "universButtonPressed",
    )
    val style = universButtonStyle(state.status, pressedFraction)
    val statusText =
        stringResource(
            when (state.status) {
                UniversStatus.NOT_STARTED -> R.string.univers_state_not_started
                UniversStatus.GOING -> R.string.univers_state_going
                UniversStatus.IS_FINISHED -> R.string.univers_state_finished
            },
        )
    val label: @Composable (Modifier) -> Unit = { labelModifier ->
        Text(
            text = stringResource(state.nameRes).uppercase(Locale.current.platformLocale),
            style =
                DefaultTextStyles.body.copy(
                    letterSpacing = LETTER_SPACING,
                    color = style.textColor,
                ),
            textAlign = TextAlign.Center,
            modifier = labelModifier,
        )
    }

    Box(modifier = modifier.padding(top = BADGE_OVERHANG)) {
        Button(
            onClick = onClick,
            enabled = state.clickable,
            interactionSource = interactionSource,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .graphicsLayer {
                        val scale = 1f - (1f - PRESSED_SCALE) * pressedFraction
                        scaleX = scale
                        scaleY = scale
                    }
                    .then(
                        if (style.shadowColor != null) {
                            Modifier.shadow(
                                elevation = BUTTON_SHADOW_ELEVATION,
                                shape = BUTTON_SHAPE,
                                ambientColor = style.shadowColor,
                                spotColor = style.shadowColor,
                            )
                        } else {
                            Modifier
                        },
                    )
                    .background(style.fill, BUTTON_SHAPE)
                    .semantics { stateDescription = statusText },
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
            contentPadding = PaddingValues(BUTTON_CONTENT_PADDING),
        ) {
            if (horizontal) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(CONTENT_SPACING),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    state.image?.let { UniversImage(it, state.status, Modifier.size(IMAGE_SIZE_HORIZONTAL)) }
                    label(Modifier.weight(1f))
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(BUTTON_CONTENT_PADDING),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    state.image?.let {
                        UniversImage(
                            it,
                            state.status,
                            Modifier
                                .widthIn(max = IMAGE_MAX_WIDTH_VERTICAL)
                                .fillMaxWidth()
                                .aspectRatio(1f),
                        )
                    }
                    label(Modifier)
                }
            }
        }
        if (state.status == UniversStatus.IS_FINISHED) {
            CheckBadge(
                animate = state.animateCheck,
                onShown = onCheckShown,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = BADGE_OVERHANG / 2, y = -BADGE_OVERHANG),
            )
        }
    }
}

/** Decorative: the button around it carries the name and the status. */
@Composable
private fun UniversImage(
    @DrawableRes image: Int,
    status: UniversStatus,
    modifier: Modifier = Modifier,
) {
    val locked = status == UniversStatus.NOT_STARTED

    Image(
        painter = painterResource(image),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        colorFilter = if (locked) GRAYSCALE else null,
        alpha = if (locked) LOCKED_IMAGE_ALPHA else 1f,
        modifier = modifier.clip(RoundedCornerShape(IMAGE_CORNER_RADIUS)),
    )
}

/** Green disc with a white check, popping in when [animate] (the first time the universe shows up as finished). */
@Composable
private fun CheckBadge(
    animate: Boolean,
    onShown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale = remember { Animatable(if (animate) 0f else 1f) }
    val lifecycle = (LocalContext.current as? LifecycleOwner)?.lifecycle
    // Waits for the screen to be in front and its slide-in to be over, otherwise the pop-in is played (and
    // reported as seen) before anyone can see it. Reported once the pop-in is over: if the screen is torn down
    // before that, the next one plays it again.
    LaunchedEffect(Unit) {
        if (animate) {
            lifecycle?.withResumed { }
            delay(CHECK_POP_DELAY_MS)
            scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
            onShown()
        }
    }

    Box(
        modifier =
            modifier
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                .size(BADGE_SIZE)
                .shadow(elevation = 3.dp, shape = CircleShape)
                .background(
                    Brush.verticalGradient(listOf(ColorSuccess, ColorSuccessDark)),
                    CircleShape,
                )
                .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_check),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(BADGE_SIZE * BADGE_ICON_RATIO),
        )
    }
}

@Preview(name = "Univers - portrait", widthDp = 360)
@Composable
private fun UniversButtonHorizontalPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            previewCards.forEach {
                UniversButton(
                    it,
                    onClick = {},
                    horizontal = true,
                    onCheckShown = {},
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(name = "Univers - paysage", widthDp = 720)
@Composable
private fun UniversButtonVerticalPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            previewCards.forEach {
                UniversButton(
                    it,
                    onClick = {},
                    horizontal = false,
                    onCheckShown = {},
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

internal val previewCards =
    listOf(
        UniversCardState(
            R.drawable.image_carnet_bord1,
            R.string.univers1_name,
            UniversStatus.IS_FINISHED,
            false,
            "",
            "1",
        ),
        UniversCardState(
            R.drawable.image_carnet_bord2,
            R.string.univers2_name,
            UniversStatus.GOING,
            true,
            "",
            "2",
        ),
        UniversCardState(
            R.drawable.image_carnet_bord3,
            R.string.univers3_name_locked,
            UniversStatus.NOT_STARTED,
            false,
            "",
            "3",
        ),
    )
