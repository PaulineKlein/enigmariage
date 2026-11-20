package com.pklein.mariage.presentation.trophies.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val CONTENT_MAX_WIDTH = 600.dp
private val BACK_ICON_SIZE = 24.dp

/** Every trophy with its unlocked status; locked hidden trophies show "???" instead of their title. */
@Composable
fun TrophyScreen(
    trophies: List<Pair<Trophies, Boolean>>,
    onBackClick: () -> Unit,
) {
    val resources = LocalResources.current
    val missingCount = trophies.count { !it.second }
    val subtitle =
        if (missingCount == 0) {
            stringResource(R.string.trophies_complete)
        } else {
            resources.getQuantityString(R.plurals.trophies_missing, missingCount, missingCount)
        }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier =
                    Modifier
                        .widthIn(max = CONTENT_MAX_WIDTH)
                        .weight(1f),
            ) {
                TrophyHeader(onBackClick = onBackClick)

                Text(
                    text = subtitle,
                    style = DefaultTextStyles.bodyLink,
                    textAlign = TextAlign.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 12.dp),
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                ) {
                    items(trophies, key = { it.first.name }) { (trophy, unlocked) ->
                        TrophyItem(trophy = trophy, unlocked = unlocked)
                    }
                }
            }
        }
    }
}

@Composable
private fun TrophyHeader(onBackClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = stringResource(R.string.home_accessibility),
            modifier =
                Modifier
                    .size(BACK_ICON_SIZE)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onBackClick,
                    ),
        )
        Text(
            text = stringResource(R.string.trophies_titre),
            style = DefaultTextStyles.titre,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
        )
    }
}

private val previewTrophies =
    Trophies.entries.take(6).mapIndexed { index, trophy -> trophy to (index % 2 == 0) }

@Preview(showSystemUi = true, name = "Trophées")
@Composable
private fun TrophyScreenPreview() {
    TrophyScreen(trophies = previewTrophies, onBackClick = {})
}

@Preview(showSystemUi = true, name = "Trophées - collection complète")
@Composable
private fun TrophyScreenCompletePreview() {
    TrophyScreen(
        trophies = previewTrophies.map { it.first to true },
        onBackClick = {},
    )
}

@Preview(showSystemUi = true, name = "Trophées - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun TrophyScreenLandscapePreview() {
    TrophyScreen(trophies = previewTrophies, onBackClick = {})
}
