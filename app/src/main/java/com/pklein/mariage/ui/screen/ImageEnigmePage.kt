package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.ZoomHint
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

/** Home button, title, message and a zoomable image, like fragment_salle_cocktails_one_1.xml. */
@Composable
fun ImageEnigmePage(
    @StringRes title: Int,
    message: String,
    image: Int,
    imageDescription: String,
    onHomeClick: () -> Unit,
    onImageClick: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MyNavigationBar(
            title = title,
            onHomeClick = onHomeClick,
        )

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
                Text(
                    text = message,
                    style = DefaultTextStyles.bodyBig,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                )
                Image(
                    painter = painterResource(image),
                    contentDescription = imageDescription,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clickable { onImageClick(image) },
                )
                ZoomHint(modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Preview(name = "Page image", showSystemUi = true)
@Composable
private fun ImageEnigmePagePreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ImageEnigmePage(
                title = R.string.reception_titre,
                message = "Cherchons un indice dans cette image !",
                image = R.drawable.image_maze_japan,
                imageDescription = "Image de l'énigme",
                onHomeClick = {},
                onImageClick = {},
            )
        }
    }
}
