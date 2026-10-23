package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun MyNavigationBar(
    @StringRes title: Int,
    onHomeClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.ic_home),
            contentDescription = stringResource(id = R.string.home_accessibility),
            modifier =
                Modifier
                    .size(32.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onHomeClick,
                    ),
        )
        Text(
            text = stringResource(id = title),
            style = DefaultTextStyles.titre,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyNavigationBarPreview() {
    AppTheme {
        MyNavigationBar(
            title = R.string.diner_titre_tanzanie,
            onHomeClick = {},
        )
    }
}
