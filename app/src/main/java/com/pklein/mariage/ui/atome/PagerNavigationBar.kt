package com.pklein.mariage.ui.atome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.AppTheme
import kotlinx.coroutines.launch

private val BOTTOM_BAR_HEIGHT = 48.dp

/** Dots in the middle and a nudging next arrow on the right, which fades out on the last page. */
@Composable
fun PagerNavigationBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(BOTTOM_BAR_HEIGHT),
        contentAlignment = Alignment.Center,
    ) {
        PagerDots(pageCount = pagerState.pageCount, currentPage = pagerState.currentPage)

        AnimatedVisibility(
            visible = pagerState.currentPage < pagerState.pageCount - 1,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            NextArrow(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
            )
        }
    }
}

@Preview(name = "Première page")
@Composable
private fun PagerNavigationBarFirstPagePreview() {
    AppTheme {
        PagerNavigationBar(
            pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 }),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Dernière page")
@Composable
private fun PagerNavigationBarLastPagePreview() {
    AppTheme {
        PagerNavigationBar(
            pagerState = rememberPagerState(initialPage = 1, pageCount = { 2 }),
            modifier = Modifier.padding(16.dp),
        )
    }
}
