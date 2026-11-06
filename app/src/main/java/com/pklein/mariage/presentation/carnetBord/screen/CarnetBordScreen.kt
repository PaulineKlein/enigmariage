package com.pklein.mariage.presentation.carnetBord.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val PORTRAIT_PADDING = 32.dp
private val LANDSCAPE_PADDING = 16.dp
private val LANDSCAPE_CARDS_SPACING = 8.dp
private const val TROPHIES_BUTTON_WIDTH_RATIO = 0.7f

/** Logbook listing the three universes and the trophies button, replaces activity_carnet_bord.xml (portrait and landscape). */
@Composable
fun CarnetBordScreen(
    univers: List<UniversCardState>,
    unlockedTrophies: Int,
    totalTrophies: Int,
    onUniversClick: (step: String) -> Unit,
    onTrophiesClick: () -> Unit,
) {
    val landscape = isLandscape()

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(if (landscape) LANDSCAPE_PADDING else PORTRAIT_PADDING),
        ) {
            Text(
                text = stringResource(R.string.carnet_bord_titre),
                style = DefaultTextStyles.titre,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            // Scrollable so small screens and big fonts never clip, yet evenly spread when there is room.
            BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
                val availableHeight = maxHeight
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .heightIn(min = availableHeight),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (landscape) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(LANDSCAPE_CARDS_SPACING),
                            verticalAlignment = Alignment.Top,
                        ) {
                            univers.forEach { card ->
                                UniversButton(
                                    state = card,
                                    onClick = { onUniversClick(card.step) },
                                    horizontal = false,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    } else {
                        univers.forEach { card ->
                            UniversButton(
                                state = card,
                                onClick = { onUniversClick(card.step) },
                                horizontal = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    AppButton(
                        text =
                            stringResource(
                                R.string.trophees_progress_button,
                                unlockedTrophies,
                                totalTrophies,
                            ),
                        onClick = onTrophiesClick,
                        modifier = Modifier.fillMaxWidth(TROPHIES_BUTTON_WIDTH_RATIO),
                    )
                }
            }
        }
    }
}

@Composable
private fun isLandscape() = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

@Preview(name = "Carnet de bord", showSystemUi = true)
@Composable
private fun CarnetBordScreenPreview() {
    CarnetBordScreen(
        univers = previewCards,
        unlockedTrophies = 3,
        totalTrophies = 12,
        onUniversClick = {},
        onTrophiesClick = {},
    )
}

@Preview(name = "Carnet de bord paysage", device = "spec:width=891dp,height=411dp")
@Composable
private fun CarnetBordScreenLandscapePreview() {
    CarnetBordScreen(
        univers = previewCards,
        unlockedTrophies = 3,
        totalTrophies = 12,
        onUniversClick = {},
        onTrophiesClick = {},
    )
}
