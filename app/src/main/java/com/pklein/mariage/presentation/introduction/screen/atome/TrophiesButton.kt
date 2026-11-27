package com.pklein.mariage.presentation.introduction.screen.atome

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.presentation.introduction.screen.TrophyProgress
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.theme.AppTheme

@Composable
fun TrophiesButton(
    trophyProgress: TrophyProgress,
    onTrophies: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text =
            stringResource(
                R.string.trophees_progress_button,
                trophyProgress.unlocked,
                trophyProgress.total,
            ),
        onClick = onTrophies,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(name = "Trophées", widthDp = 320)
@Composable
private fun TrophiesButtonPreview() {
    AppTheme {
        TrophiesButton(
            trophyProgress = TrophyProgress(unlocked = 3, total = 12),
            onTrophies = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
