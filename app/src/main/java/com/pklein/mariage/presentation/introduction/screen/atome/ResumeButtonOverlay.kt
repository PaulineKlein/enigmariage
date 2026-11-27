package com.pklein.mariage.presentation.introduction.screen.atome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.LETTER_SPACING
import com.pklein.mariage.ui.atome.TOP_LIGHTEN
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.GradientEndColor
import com.pklein.mariage.ui.theme.GradientEndColorFonce

private val BUTTON_SHAPE = RoundedCornerShape(12.dp)
private const val SCRIM_ALPHA = 0.4f
private const val BUTTON_FILL_ALPHA = 0.8f

private class OverlaySizes(
    val margin: Dp,
    val verticalPadding: Dp,
    val icon: Dp,
    val label: TextUnit,
)

private val REGULAR_SIZES =
    OverlaySizes(margin = 12.dp, verticalPadding = 12.dp, icon = 22.dp, label = 18.sp)
private val COMPACT_SIZES =
    OverlaySizes(margin = 8.dp, verticalPadding = 8.dp, icon = 16.dp, label = 14.sp)

/**
 * Laid over the bottom of the boat picture to show it is a button: a soft dark fade, then a floating, slightly
 * transparent "▶ Reprendre" button in the look of the app's buttons. It only draws; the picture it sits on takes the tap.
 * [compact] shrinks it for a small picture so its label stays on one line. It must not measure its own width
 * (no `BoxWithConstraints`): it sits in a row sized with intrinsic measurements, which that does not support.
 */
@Composable
fun ResumeButtonOverlay(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
) {
    val sizes = if (compact) COMPACT_SIZES else REGULAR_SIZES
    Box(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = SCRIM_ALPHA),
                            ),
                        ),
                    ).padding(
                        top = sizes.margin * 2,
                        start = sizes.margin,
                        end = sizes.margin,
                        bottom = sizes.margin,
                    ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    lerp(
                                        GradientEndColor,
                                        Color.White,
                                        TOP_LIGHTEN,
                                    ).copy(alpha = BUTTON_FILL_ALPHA),
                                    GradientEndColor.copy(alpha = BUTTON_FILL_ALPHA),
                                ),
                            ),
                            BUTTON_SHAPE,
                        ).border(BorderStroke(1.dp, GradientEndColorFonce), BUTTON_SHAPE)
                        .padding(vertical = sizes.verticalPadding),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = null,
                    tint = ColorBleuFonce,
                    modifier = Modifier.size(sizes.icon),
                )
                Text(
                    text = stringResource(R.string.splash_resume).uppercase(Locale.current.platformLocale),
                    style =
                        DefaultTextStyles.body.copy(
                            fontSize = sizes.label,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = LETTER_SPACING,
                            color = ColorBleuFonce,
                        ),
                    maxLines = 1,
                    softWrap = false,
                )
            }
        }
    }
}

@Preview(name = "Bouton Reprendre sur l'image", widthDp = 320)
@Composable
private fun ResumeButtonOverlayPreview() {
    AppTheme {
        ResumeButtonOverlayOnSky(modifier = Modifier.padding(16.dp))
    }
}

@Preview(name = "Bouton Reprendre, petite image (paysage)", widthDp = 200)
@Composable
private fun ResumeButtonOverlayCompactPreview() {
    AppTheme {
        ResumeButtonOverlayOnSky(compact = true, modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun ResumeButtonOverlayOnSky(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
) {
    Box(modifier = modifier.fillMaxWidth().padding(top = 80.dp).background(Color(0xFF8EC5E8))) {
        ResumeButtonOverlay(modifier = Modifier.align(Alignment.BottomCenter), compact = compact)
    }
}
