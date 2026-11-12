package com.pklein.mariage.presentation.salleCocktails.screen

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
import androidx.compose.foundation.layout.IntrinsicSize
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
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private val CARD_CORNER_RADIUS = 16.dp
private val CARD_SPACING = 16.dp
private val BADGE_SIZE = 28.dp
private const val CARD_SELECTED_SCALE = 1.04f
private const val CARD_IDLE_ALPHA = 0.7f
private const val ZONE_IMAGE_RATIO = 1f
private const val NO_ZONE = 0
const val ZONE_DESERT = 1
const val ZONE_VILLAGE = 2

private data class Zone(
    val id: Int,
    @DrawableRes val image: Int,
    @StringRes val label: Int,
)

private val zones =
    listOf(
        Zone(ZONE_DESERT, R.drawable.image_village_desert, R.string.zone_desert),
        Zone(ZONE_VILLAGE, R.drawable.image_village_village, R.string.zone_village),
    )

/** Pick the region where Nox found the cactus juice, then validate. */
@Composable
fun CocktailsFourScreen(
    playerName: String,
    onHomeClick: () -> Unit,
    onValidateClick: (Int) -> Unit,
) {
    var selected by rememberSaveable { mutableIntStateOf(NO_ZONE) }

    AppTheme {
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
                    title = R.string.cocktail_one_titre,
                    onHomeClick = onHomeClick,
                )

                Column(
                    modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = annotatedStringResource(R.string.cocktail_four_message, playerName),
                        style = DefaultTextStyles.body,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    ZonesRow(selected = selected, onSelect = { selected = it })
                }
            }

            AppButton(
                text = stringResource(id = R.string.valider),
                onClick = { onValidateClick(selected) },
                enabled = selected != NO_ZONE,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = CONTENT_MAX_WIDTH)
                        .fillMaxWidth(0.7f),
            )
        }
    }
}

@Composable
private fun ZonesRow(
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    val view = LocalView.current
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(CARD_SPACING),
    ) {
        zones.forEach { zone ->
            ZoneCard(
                zone = zone,
                isSelected = selected == zone.id,
                onClick = {
                    if (selected != zone.id) {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    onSelect(zone.id)
                },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ZoneCard(
    zone: Zone,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) CARD_SELECTED_SCALE else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "zoneScale",
    )
    val shape = RoundedCornerShape(CARD_CORNER_RADIUS)

    Box(
        modifier =
            modifier.graphicsLayer {
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
                painter = painterResource(zone.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(ZONE_IMAGE_RATIO)
                        .clip(RoundedCornerShape(8.dp)),
            )
            Text(
                text = stringResource(zone.label),
                style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
            )
        }
        if (isSelected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
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

@Preview(showSystemUi = true, name = "Cocktails 4")
@Composable
private fun CocktailsFourScreenPreview() {
    CocktailsFourScreen(
        playerName = "Pauline",
        onHomeClick = {},
        onValidateClick = {},
    )
}
