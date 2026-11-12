package com.pklein.mariage.ui.atome

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorHint
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val CORNER_RADIUS = 14.dp
private val SPACING = 10.dp
private val INDICATOR_SIZE = 24.dp
private val INDICATOR_DOT_SIZE = 12.dp
private const val SELECTED_SCALE = 1.03f
private const val IDLE_ALPHA = 0.7f

/** Vertical list of [RadioCard]s that TalkBack announces as a single selectable group. */
@Composable
fun RadioCardGroup(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(SPACING),
        content = content,
    )
}

/** Selectable card with an animated radio dot; [content] is laid out in a row after the dot. */
@Composable
fun RadioCard(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val view = LocalView.current
    val scale by animateFloatAsState(
        targetValue = if (isSelected) SELECTED_SCALE else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "radioCardScale",
    )
    val shape = RoundedCornerShape(CORNER_RADIUS)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }.then(if (isSelected) Modifier.shadow(6.dp, shape) else Modifier)
                .clip(shape)
                .background(Color.White.copy(alpha = if (isSelected) 1f else IDLE_ALPHA))
                .border(
                    border =
                        if (isSelected) {
                            BorderStroke(3.dp, ColorAccent)
                        } else {
                            BorderStroke(1.dp, Color.White)
                        },
                    shape = shape,
                ).selectable(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onClick()
                    },
                    role = Role.RadioButton,
                ).padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(INDICATOR_SIZE)
                    .border(2.dp, if (isSelected) ColorAccent else ColorHint, CircleShape)
                    .clearAndSetSemantics {},
        ) {
            if (isSelected) {
                Box(
                    modifier =
                        Modifier
                            .size(INDICATOR_DOT_SIZE)
                            .background(ColorAccent, CircleShape),
                )
            }
        }
        content()
    }
}

/** [RadioCard] with a single text label, bold once selected. */
@Composable
fun RadioCard(
    text: AnnotatedString,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RadioCard(isSelected = isSelected, onClick = onClick, modifier = modifier) {
        Text(
            text = text,
            style =
                DefaultTextStyles.bodyBig.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                ),
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, name = "RadioCardGroup", widthDp = 360)
@Composable
private fun RadioCardGroupPreview() {
    AppTheme {
        RadioCardGroup(modifier = Modifier.padding(16.dp)) {
            RadioCard(text = AnnotatedString("Réponse sélectionnée"), isSelected = true, onClick = {})
            RadioCard(text = AnnotatedString("Autre réponse"), isSelected = false, onClick = {})
            RadioCard(text = AnnotatedString("Encore une réponse"), isSelected = false, onClick = {})
        }
    }
}
