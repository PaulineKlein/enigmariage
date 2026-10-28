package com.pklein.mariage.presentation.salleReception.salleCuisine.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer

@Composable
fun CuisineTwoScreen(
    onHomeClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
        ) {
            MyNavigationBar(
                title = R.string.cuisine_titre,
                onHomeClick = onHomeClick,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = annotatedStringResource(id = R.string.cuisine_two_message),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(R.drawable.image_cuisine),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            showImageWithStfalconViewer(
                                context,
                                R.drawable.image_cuisine,
                            )
                        },
            )
            Image(
                painter = painterResource(R.drawable.image_gourde_dog),
                contentDescription = stringResource(R.string.salle_cocktails_gourde_accessibility_dog),
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .width(300.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            showImageWithStfalconViewer(
                                context,
                                R.drawable.image_gourde_dog,
                            )
                        },
            )
        }

        AppButton(
            text = stringResource(id = R.string.continuer),
            onClick = onContinueClick,
            enabled = true,
            modifier =
                Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f),
        )
    }
}

@Preview(showBackground = true, name = "Réception 7")
@Composable
private fun CuisineTwoScreenPreview() {
    AppTheme {
        CuisineTwoScreen(
            onHomeClick = {},
            onContinueClick = {},
        )
    }
}
