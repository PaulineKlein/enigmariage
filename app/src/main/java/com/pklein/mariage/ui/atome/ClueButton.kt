package com.pklein.mariage.ui.atome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.DefaultTextStyles

/** Compose equivalent of layout/indice.xml. */
@Composable
fun ClueButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .height(48.dp)
                .clip(shape)
                .background(ColorHelp)
                .border(1.dp, ColorHelpFonce, shape)
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 13.dp),
    ) {
        Text(
            text = text,
            style = DefaultTextStyles.body.copy(fontSize = 16.sp, color = ColorHelpText),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 8.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorHelpText),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Preview(name = "Indice")
@Composable
private fun ClueButtonPreview() {
    AppTheme {
        ClueButton(
            text = "Indice",
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
