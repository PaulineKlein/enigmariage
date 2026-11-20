package com.pklein.mariage.ui.atome

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.ColorBleuFonce

private const val ARROW_NUDGE_DP = 8f
private const val ARROW_NUDGE_DURATION_MS = 700

/** Arrow button nudging towards the next page. */
@Composable
fun NextArrow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "nextArrow")
    val nudge by transition.animateFloat(
        initialValue = 0f,
        targetValue = ARROW_NUDGE_DP,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = ARROW_NUDGE_DURATION_MS,
                        easing = FastOutSlowInEasing,
                    ),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "nextArrowNudge",
    )

    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_next),
            contentDescription = stringResource(R.string.introduction_suivant),
            tint = ColorBleuFonce,
            modifier =
                Modifier
                    .size(32.dp)
                    .graphicsLayer { translationX = nudge.dp.toPx() },
        )
    }
}
