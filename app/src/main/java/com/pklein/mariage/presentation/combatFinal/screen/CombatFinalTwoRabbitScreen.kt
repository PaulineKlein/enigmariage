package com.pklein.mariage.presentation.combatFinal.screen

import android.view.HapticFeedbackConstants
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private val MAZE_MAX_WIDTH = 300.dp
private val CARD_CORNER_RADIUS = 16.dp
private val CARD_SPACING = 12.dp
private const val CARD_SELECTED_SCALE = 1.04f
private const val CARD_IDLE_ALPHA = 0.7f
private const val COUP_IMAGE_RATIO = 142f / 170f
private val BADGE_SIZE = 28.dp
private const val NO_COUP = 0
private const val COUPS_PER_ROW = 2

private data class Coup(
    @DrawableRes val image: Int,
    @StringRes val text: Int,
)

private val coups =
    listOf(
        Coup(R.drawable.image_coup_empi_uchi, R.string.combatFinal_coup_1),
        Coup(R.drawable.image_coup_mawashi_geri, R.string.combatFinal_coup_2),
        Coup(R.drawable.image_coup_yoko_geri, R.string.combatFinal_coup_3),
        Coup(R.drawable.image_coup_yama_zuki, R.string.combatFinal_coup_4),
        Coup(R.drawable.image_coup_gyaku_zuki, R.string.combatFinal_coup_5),
    )

/** Final fight against Nox: read the maze, pick the right strike (1-based) and attack. */
@Composable
fun CombatFinalTwoRabbitScreen(
    onHomeClick: () -> Unit,
    onValidateClick: (Int) -> Unit,
) {
    var selected by rememberSaveable { mutableIntStateOf(NO_COUP) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MyNavigationBar(
                title = R.string.combatFinal_titre,
                onHomeClick = onHomeClick,
            )

            Column(
                modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = annotatedStringResource(id = R.string.combatFinal_two_message_1),
                    style = DefaultTextStyles.bodyBig,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = annotatedStringResource(id = R.string.combatFinal_two_message_2),
                    style = DefaultTextStyles.bodyBig,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(R.drawable.image_maze_final_rabbit),
                    contentDescription = stringResource(R.string.salle_combat_final_accessibility),
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .widthIn(max = MAZE_MAX_WIDTH)
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(CARD_CORNER_RADIUS))
                            .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                            .border(3.dp, Color.White, RoundedCornerShape(CARD_CORNER_RADIUS)),
                )

                Spacer(modifier = Modifier.height(24.dp))

                CoupsGrid(selected = selected, onSelect = { selected = it })
            }
        }

        AppButton(
            text = stringResource(id = R.string.combatFinal_two_attack),
            onClick = { onValidateClick(selected) },
            enabled = selected != NO_COUP,
            textSize = 18.sp,
            contentPadding = 16.dp,
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = CONTENT_MAX_WIDTH)
                    .fillMaxWidth(0.8f),
        )
    }
}

/** The strikes as two columns of selectable cards (the last, odd one is centered). */
@Composable
private fun CoupsGrid(
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    val view = LocalView.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(CARD_SPACING),
    ) {
        coups.withIndex().toList().chunked(COUPS_PER_ROW).forEach { rowCoups ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(CARD_SPACING),
            ) {
                val missing = COUPS_PER_ROW - rowCoups.size
                if (missing > 0) Spacer(modifier = Modifier.weight(missing / 2f))
                rowCoups.forEach { (index, coup) ->
                    val number = index + 1
                    CoupCard(
                        coup = coup,
                        isSelected = selected == number,
                        onClick = {
                            if (selected != number) {
                                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }
                            onSelect(number)
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (missing > 0) Spacer(modifier = Modifier.weight(missing / 2f))
            }
        }
    }
}

@Composable
private fun CoupCard(
    coup: Coup,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) CARD_SELECTED_SCALE else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "coupScale",
    )
    val shape = RoundedCornerShape(CARD_CORNER_RADIUS)

    Box(
        modifier =
            modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .then(if (isSelected) Modifier.shadow(6.dp, shape) else Modifier)
                    .clip(shape)
                    .background(Color.White.copy(alpha = if (isSelected) 1f else CARD_IDLE_ALPHA))
                    .border(
                        border =
                            if (isSelected) {
                                BorderStroke(3.dp, ColorAccent)
                            } else {
                                BorderStroke(1.dp, Color.White)
                            },
                        shape = shape,
                    )
                    .selectable(
                        selected = isSelected,
                        onClick = onClick,
                        role = Role.RadioButton,
                    )
                    .padding(8.dp),
        ) {
            Image(
                painter = painterResource(coup.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(COUP_IMAGE_RATIO)
                        .clip(RoundedCornerShape(8.dp)),
            )
            Text(
                text = stringResource(coup.text),
                style = DefaultTextStyles.bodyLink,
                textAlign = TextAlign.Center,
            )
        }
        if (isSelected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(BADGE_SIZE)
                        .background(ColorAccent, CircleShape)
                        .clearAndSetSemantics {},
            ) {
                Text(
                    text = "✓",
                    style =
                        DefaultTextStyles.bodyBig.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                )
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Combat final Nox")
@Composable
private fun CombatFinalTwoRabbitScreenPreview() {
    AppTheme {
        CombatFinalTwoRabbitScreen(
            onHomeClick = {},
            onValidateClick = {},
        )
    }
}
