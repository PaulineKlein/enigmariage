package com.pklein.mariage.ui.modifier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.Density
import kotlin.math.roundToInt

private const val CONTENT_VERTICAL_BIAS = 0.4f

/** Packs the children together and places the group at [CONTENT_VERTICAL_BIAS] of the free space, like the old constraint-layout chain. */
val UpperThirdArrangement =
    object : Arrangement.Vertical {
        override fun Density.arrange(
            totalSize: Int,
            sizes: IntArray,
            outPositions: IntArray,
        ) {
            var y = ((totalSize - sizes.sum()) * CONTENT_VERTICAL_BIAS).roundToInt().coerceAtLeast(0)
            sizes.forEachIndexed { index, size ->
                outPositions[index] = y
                y += size
            }
        }
    }
