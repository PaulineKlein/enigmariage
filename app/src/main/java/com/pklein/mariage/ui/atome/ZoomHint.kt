package com.pklein.mariage.ui.atome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun ZoomHint(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .background(ColorHelp, RoundedCornerShape(10.dp))
                .padding(8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.zoom_explication),
            style = DefaultTextStyles.body,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ZoomHintPreview() {
    AppTheme {
        ZoomHint()
    }
}
