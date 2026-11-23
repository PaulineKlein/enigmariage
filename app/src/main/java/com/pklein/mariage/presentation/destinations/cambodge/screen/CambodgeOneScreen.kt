package com.pklein.mariage.presentation.destinations.cambodge.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.ZoomHint
import com.pklein.mariage.ui.screen.CadenaPage
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.screen.TwoPageScreen
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.ui.theme.gameAnnotatedStringResource
import com.pklein.mariage.ui.theme.gameStringResource

/** The home bar stays above two swipeable pages: Angkor's zoomable picture, then the code to find in it. */
@Composable
fun CambodgeOneScreen(
    onHomeClick: () -> Unit,
    onImageClick: (Int) -> Unit,
    onValidateClick: (String) -> Unit,
) {
    TwoPageScreen(
        header = {
            MyNavigationBar(
                title = R.string.diner_titre_cambodge,
                onHomeClick = onHomeClick,
            )
        },
        firstPage = { AngkorPage(onImageClick = onImageClick) },
        secondPage = {
            CadenaPage(
                question = gameStringResource(R.string.diner_cambodge_one_message_3),
                onValidateClick = onValidateClick,
            )
        },
    )
}

@Composable
private fun AngkorPage(onImageClick: (Int) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (maxWidth > maxHeight) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AngkorMessages()
                    ZoomHint()
                }
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    AngkorImage(
                        contentScale = ContentScale.Fit,
                        onImageClick = onImageClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = maxHeight),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AngkorMessages()
                AngkorImage(
                    contentScale = ContentScale.FillWidth,
                    onImageClick = onImageClick,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                )
                ZoomHint()
            }
        }
    }
}

@Composable
private fun AngkorMessages() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = annotatedStringResource(R.string.diner_cambodge_one_message_1),
            style = DefaultTextStyles.bodyBig,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = gameAnnotatedStringResource(R.string.diner_cambodge_one_message_2),
            style = DefaultTextStyles.body,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AngkorImage(
    contentScale: ContentScale,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.image_angkor_couple),
        contentDescription = stringResource(R.string.image_enigme_accessibility),
        contentScale = contentScale,
        modifier = modifier.clickable { onImageClick(R.drawable.image_angkor_couple) },
    )
}

@Preview(showSystemUi = true, name = "Cambodge 1")
@Composable
private fun CambodgeOneScreenPreview() {
    CambodgeOneScreen(
        onHomeClick = {},
        onImageClick = {},
        onValidateClick = {},
    )
}

@Preview(showSystemUi = true, name = "Cambodge 1 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun CambodgeOneScreenLandscapePreview() {
    CambodgeOneScreen(
        onHomeClick = {},
        onImageClick = {},
        onValidateClick = {},
    )
}

@Preview(showSystemUi = true, name = "Cambodge 1 - page image")
@Composable
private fun AngkorPagePreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AngkorPage(onImageClick = {})
        }
    }
}
