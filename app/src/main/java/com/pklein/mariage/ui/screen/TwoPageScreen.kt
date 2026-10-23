package com.pklein.mariage.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.PagerDots
import com.pklein.mariage.ui.theme.AppTheme
import kotlin.math.abs
import kotlin.math.max

private const val PAGE_COUNT = 2
private const val MIN_SCALE = 0.95f
private const val MIN_ALPHA = 0.6f

/** Two swipeable pages with dots and a zoom-out transition, equivalent to a 2-item ViewPager2. */
@Composable
fun TwoPageScreen(
    firstPage: @Composable () -> Unit,
    secondPage: @Composable () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(16.dp),
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
            ) { page ->
                Box(modifier = Modifier.fillMaxSize().zoomOutTransform(pagerState, page)) {
                    if (page == 0) firstPage() else secondPage()
                }
            }

            PagerDots(pageCount = PAGE_COUNT, currentPage = pagerState.currentPage)
        }
    }
}

/** Port of ZoomOutPageTransformer: pages shrink and fade as they leave the center. */
private fun Modifier.zoomOutTransform(
    pagerState: PagerState,
    page: Int,
): Modifier =
    graphicsLayer {
        val position = abs((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
        val scaleFactor = max(MIN_SCALE, 1 - position)
        scaleX = scaleFactor
        scaleY = scaleFactor
        alpha = MIN_ALPHA + ((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)
    }

@Preview(name = "Image + cadenas", showSystemUi = true)
@Composable
private fun TwoPageScreenPreview() {
    TwoPageScreen(
        firstPage = {
            ImageEnigmePage(
                title = R.string.reception_titre,
                message = "Cherchons un indice dans cette image !\nCompte le nombre de bateaux, de lunettes et d'objets roses.",
                image = R.drawable.image_maze_japan,
                imageDescription = "Image de l'énigme",
                onHomeClick = {},
                onImageClick = {},
            )
        },
        secondPage = {
            CadenaPage(
                question = "Place les chiffres obtenus dans l'ordre croissant.",
                onValidateClick = {},
            )
        },
    )
}
