package com.pklein.mariage.presentation.destinations.tanzanie.screen.atome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorBleuFonce
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun PairBadge(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .padding(4.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(ColorAccent),
    ) {
        Text(text = number.toString(), style = DefaultTextStyles.body.copy(color = Color.White))
    }
}

fun Modifier.cellFrame(isSelected: Boolean): Modifier {
    val shape = RoundedCornerShape(12.dp)
    return fillMaxWidth()
        .aspectRatio(1f)
        .clip(shape)
        .background(Color.White)
        .border(
            width = if (isSelected) 4.dp else 2.dp,
            color = if (isSelected) ColorAccent else ColorBleuFonce,
            shape = shape,
        ).padding(6.dp)
}
