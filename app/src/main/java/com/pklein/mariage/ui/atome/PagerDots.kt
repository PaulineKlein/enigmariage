package com.pklein.mariage.ui.atome

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorBleuLightFonce
import com.pklein.mariage.ui.theme.ColorDotUnselected

/** Compose equivalent of ViewPager2DotsUtils (drawable/dot_selected and dot_unselected). */
@Composable
fun PagerDots(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 2.dp)
                        .size(24.dp)
                        .border(
                            width = 2.dp,
                            color = if (index == currentPage) ColorBleuLightFonce else ColorDotUnselected,
                            shape = CircleShape,
                        ),
            )
        }
    }
}

@Preview(name = "Deux pages")
@Composable
private fun PagerDotsPreview() {
    AppTheme {
        PagerDots(pageCount = 2, currentPage = 0, modifier = Modifier.padding(16.dp))
    }
}
