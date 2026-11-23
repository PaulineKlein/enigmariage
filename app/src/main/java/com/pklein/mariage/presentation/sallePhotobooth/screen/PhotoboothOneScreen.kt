package com.pklein.mariage.presentation.sallePhotobooth.screen

import androidx.annotation.DrawableRes
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

private val CONTENT_MAX_WIDTH = 480.dp
private const val CONTINUE_BUTTON_WIDTH_RATIO = 0.7f

/** [enigme] and [enigmeDescription] depend on the chosen inspector. */
@Composable
fun PhotoboothOneScreen(
    @DrawableRes enigme: Int,
    @StringRes enigmeDescription: Int,
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
                title = R.string.photobooth_two_titre2,
                onHomeClick = onHomeClick,
            )

            BoxWithConstraints(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                if (maxWidth > maxHeight) {
                    LandscapeContent(
                        enigme = enigme,
                        enigmeDescription = enigmeDescription,
                        onContinueClick = onContinueClick,
                    )
                } else {
                    PortraitContent(
                        enigme = enigme,
                        enigmeDescription = enigmeDescription,
                        onContinueClick = onContinueClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun PortraitContent(
    @DrawableRes enigme: Int,
    @StringRes enigmeDescription: Int,
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
                PhotoboothMessage(modifier = Modifier.padding(top = 16.dp))
                EnigmeImage(
                    enigme = enigme,
                    description = enigmeDescription,
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
private fun LandscapeContent(
    @DrawableRes enigme: Int,
    @StringRes enigmeDescription: Int,
    onContinueClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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
            PhotoboothMessage()
            ContinueButton(onClick = onContinueClick)
        }
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            EnigmeImage(
                enigme = enigme,
                description = enigmeDescription,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun PhotoboothMessage(modifier: Modifier = Modifier) {
    Text(
        text = gameAnnotatedStringResource(id = R.string.photobooth_one_message),
        style = DefaultTextStyles.bodyBig,
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(),
    )
}

@Composable
private fun EnigmeImage(
    @DrawableRes enigme: Int,
    @StringRes description: Int,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(enigme),
        contentDescription = stringResource(id = description),
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
        text = stringResource(id = R.string.continuer),
        onClick = onClick,
        modifier =
            modifier
                .widthIn(max = CONTENT_MAX_WIDTH)
                .fillMaxWidth(CONTINUE_BUTTON_WIDTH_RATIO),
    )
}

@Preview(showSystemUi = true, name = "Photobooth 1 - lapin")
@Composable
private fun PhotoboothOneScreenRabbitPreview() {
    PhotoboothOneScreen(
        enigme = R.drawable.image_gourde_propre_rabbit,
        enigmeDescription = R.string.salle_photobooth_gourde_accessibility_rabbit,
        onHomeClick = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Photobooth 1 - chien")
@Composable
private fun PhotoboothOneScreenDogPreview() {
    PhotoboothOneScreen(
        enigme = R.drawable.image_gourde_propre_dog,
        enigmeDescription = R.string.salle_photobooth_gourde_accessibility_dog,
        onHomeClick = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Photobooth 1 - paysage", widthDp = 800, heightDp = 360)
@Composable
private fun PhotoboothOneScreenLandscapePreview() {
    PhotoboothOneScreen(
        enigme = R.drawable.image_gourde_propre_rabbit,
        enigmeDescription = R.string.salle_photobooth_gourde_accessibility_rabbit,
        onHomeClick = {},
        onContinueClick = {},
    )
}
