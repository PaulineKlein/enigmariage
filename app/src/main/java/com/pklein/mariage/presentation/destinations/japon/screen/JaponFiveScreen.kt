package com.pklein.mariage.presentation.destinations.japon.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pklein.mariage.R
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource

@Composable
fun JaponFiveScreen(
    onHomeClick: () -> Unit,
    onImageClick: (Int) -> Unit,
    onValidateClick: (String) -> Unit,
) {
    QuestionScreen(
        title = R.string.diner_titre_japon,
        message = annotatedStringResource(id = R.string.diner_japon_five_question),
        enigmeImage = R.drawable.image_rebus_tapioca,
        enigmeImageDescription = stringResource(id = R.string.image_enigme_accessibility),
        enigmeImageZoomable = true,
        onImageClick = onImageClick,
        onHomeClick = onHomeClick,
        onValidateClick = onValidateClick,
    )
}

@Preview(showSystemUi = true, name = "Japon 5")
@Composable
private fun JaponFiveScreenPreview() {
    JaponFiveScreen(
        onHomeClick = {},
        onImageClick = {},
        onValidateClick = {},
    )
}
