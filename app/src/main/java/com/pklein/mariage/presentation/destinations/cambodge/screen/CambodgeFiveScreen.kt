package com.pklein.mariage.presentation.destinations.cambodge.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
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
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.screen.MyNavigationBar
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private const val CONTINUE_BUTTON_WIDTH_RATIO = 0.7f

/** The mango juice is found: a short conclusion of the Cambodia univers. */
@Composable
fun CambodgeFiveScreen(
    onHomeClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp),
        ) {
            MyNavigationBar(
                title = R.string.diner_titre_cambodge,
                onHomeClick = onHomeClick,
            )

            BoxWithConstraints(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                if (maxWidth > maxHeight) {
                    LandscapeContent(onContinueClick = onContinueClick)
                } else {
                    PortraitContent(onContinueClick = onContinueClick)
                }
            }
        }
    }
}

@Composable
private fun PortraitContent(onContinueClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .heightIn(min = maxHeight),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Message(modifier = Modifier.padding(top = 16.dp))
                MangoImage(
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .widthIn(max = CONTENT_MAX_WIDTH)
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                )
            }
        }
        ContinueButton(
            onClick = onContinueClick,
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
        )
    }
}

/** Text and button on the left, the picture fitted to the remaining height on the right. */
@Composable
private fun LandscapeContent(onContinueClick: () -> Unit) {
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
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Message()
            ContinueButton(onClick = onContinueClick)
        }
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            MangoImage(contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun Message(modifier: Modifier = Modifier) {
    Text(
        text = annotatedStringResource(R.string.diner_cambodge_five_message_1),
        style = DefaultTextStyles.bodyBig,
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(),
    )
}

@Composable
private fun MangoImage(
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.image_angkor_inspection),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Composable
private fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = stringResource(R.string.continuer),
        onClick = onClick,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(CONTINUE_BUTTON_WIDTH_RATIO),
    )
}

@Preview(showSystemUi = true, name = "Cambodge 5")
@Composable
private fun CambodgeFiveScreenPreview() {
    CambodgeFiveScreen(
        onHomeClick = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Cambodge 5 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun CambodgeFiveScreenLandscapePreview() {
    CambodgeFiveScreen(
        onHomeClick = {},
        onContinueClick = {},
    )
}
