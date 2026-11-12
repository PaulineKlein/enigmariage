package com.pklein.mariage.presentation.destinations.caraibes.screen

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.ColorCassis

private data class Spot(
    val x: Float,
    val y: Float,
    val radius: Dp,
)

private val SPOTS =
    listOf(
        Spot(0.07f, 0.22f, 9.dp),
        Spot(0.11f, 0.30f, 4.dp),
        Spot(0.04f, 0.36f, 2.5.dp),
        Spot(0.93f, 0.15f, 5.dp),
        Spot(0.88f, 0.24f, 2.5.dp),
        Spot(0.50f, 0.92f, 7.dp),
        Spot(0.56f, 0.86f, 3.dp),
        Spot(0.44f, 0.95f, 2.dp),
        Spot(0.90f, 0.80f, 10.dp),
        Spot(0.84f, 0.88f, 4.dp),
        Spot(0.95f, 0.70f, 2.5.dp),
        Spot(0.20f, 0.88f, 3.dp),
    )

/** Draws cassis stains over the content, kept small and near the edges to leave the text readable. */
fun Modifier.cassisSpots(): Modifier =
    drawWithContent {
        drawContent()
        SPOTS.forEach { spot ->
            val center = Offset(size.width * spot.x, size.height * spot.y)
            val radius = spot.radius.toPx()
            drawCircle(color = ColorCassis.copy(alpha = 0.35f), radius = radius * 1.25f, center = center)
            drawCircle(color = ColorCassis.copy(alpha = 0.6f), radius = radius, center = center)
        }
    }
