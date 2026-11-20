package com.pklein.mariage.presentation.trophies.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorPrimaryDark
import com.pklein.mariage.ui.theme.ColorText
import com.pklein.mariage.ui.theme.DefaultTextStyles

private val TROPHY_ICON_SIZE = 40.dp

/** One trophy card; a locked hidden trophy shows "???" instead of its title. */
@Composable
fun TrophyItem(
    trophy: Trophies,
    unlocked: Boolean,
) {
    val grey = colorResource(R.color.grey)
    val titleColor = if (unlocked) ColorPrimaryDark else grey
    val messageColor = if (unlocked) ColorText else grey
    val title =
        if (unlocked || !trophy.isHidden) {
            stringResource(trophy.title)
        } else {
            stringResource(R.string.trophy_locked)
        }
    val message =
        if (unlocked) stringResource(trophy.message) else stringResource(R.string.trophy_locked)
    val tint =
        if (unlocked) {
            trophy.state.tintColor?.let { colorResource(it) }
        } else {
            grey
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    RoundedCornerShape(dimensionResource(R.dimen.popup_corner)),
                )
                .padding(16.dp)
                .semantics(mergeDescendants = true) {},
    ) {
        Image(
            painter = painterResource(if (unlocked) R.drawable.ic_trophy else R.drawable.ic_lock),
            contentDescription = null,
            colorFilter = tint?.let { ColorFilter.tint(it) },
            modifier = Modifier.size(TROPHY_ICON_SIZE),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = DefaultTextStyles.sousTitre.copy(fontSize = 18.sp, color = titleColor),
            )
            Text(
                text = message,
                style = DefaultTextStyles.body.copy(fontSize = 14.sp, color = messageColor),
            )
        }
    }
}

@Preview(showBackground = true, name = "Trophée débloqué")
@Composable
private fun TrophyItemUnlockedPreview() {
    AppTheme {
        TrophyItem(trophy = Trophies.TROPHY_DOG, unlocked = true)
    }
}

@Preview(showBackground = true, name = "Trophée verrouillé")
@Composable
private fun TrophyItemLockedPreview() {
    AppTheme {
        TrophyItem(trophy = Trophies.TROPHY_DOG, unlocked = false)
    }
}
