package com.pklein.mariage.ui.modifier

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs
import kotlin.math.max

private const val MIN_SCALE = 0.95f
private const val MIN_ALPHA = 0.6f

/** Port of ZoomOutPageTransformer: pages shrink and fade as they leave the center. */
@Composable
fun Modifier.zoomOutTransform(
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
