package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.pklein.mariage.ui.theme.gameAnnotatedStringResource

private val NOX_HEIGHT = 250.dp
private val CONTENT_MAX_WIDTH = 480.dp
private const val CONTINUE_BUTTON_WIDTH_RATIO = 0.7f

/** Nox is hiding among the passengers; [description] depends on the chosen inspector. */
@Composable
fun EmbarquementTwoScreen(
    @StringRes description: Int,
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
                title = R.string.embarquement_one_titre,
                onHomeClick = onHomeClick,
            )

            BoxWithConstraints(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                if (maxWidth > maxHeight) {
                    LandscapeContent(description = description, onContinueClick = onContinueClick)
                } else {
                    PortraitContent(description = description, onContinueClick = onContinueClick)
                }
            }
        }
    }
}

@Composable
private fun PortraitContent(
    @StringRes description: Int,
    onContinueClick: () -> Unit,
) {
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
                Description(description = description, modifier = Modifier.padding(top = 16.dp))
                Image(
                    painter = painterResource(R.drawable.image_nox),
                    contentDescription = null,
                    modifier = Modifier.height(NOX_HEIGHT),
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

/** Text and button on the left, Nox fitted to the remaining height on the right. */
@Composable
private fun LandscapeContent(
    @StringRes description: Int,
    onContinueClick: () -> Unit,
) {
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
            Description(description = description)
            ContinueButton(onClick = onContinueClick)
        }
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.image_nox),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun Description(
    @StringRes description: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = gameAnnotatedStringResource(id = description),
        style = DefaultTextStyles.bodyBig,
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(),
    )
}

@Composable
private fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        text = stringResource(id = R.string.continuer),
        onClick = onClick,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(CONTINUE_BUTTON_WIDTH_RATIO),
    )
}

@Preview(showSystemUi = true, name = "Embarquement 2 - lapin")
@Composable
private fun EmbarquementTwoScreenRabbitPreview() {
    EmbarquementTwoScreen(
        description = R.string.embarquement_two_message_1_rabbit,
        onHomeClick = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Embarquement 2 - chien")
@Composable
private fun EmbarquementTwoScreenDogPreview() {
    EmbarquementTwoScreen(
        description = R.string.embarquement_two_message_1_dog,
        onHomeClick = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Embarquement 2 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun EmbarquementTwoScreenLandscapePreview() {
    EmbarquementTwoScreen(
        description = R.string.embarquement_two_message_1_rabbit,
        onHomeClick = {},
        onContinueClick = {},
    )
}
