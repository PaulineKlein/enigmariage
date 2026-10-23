package com.pklein.mariage.ui.screen

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pklein.mariage.R
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.GlassContent
import com.pklein.mariage.ui.atome.IceBlock
import com.pklein.mariage.ui.atome.IceBlockState
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.DefaultTextStyles

@Composable
fun IceBlockScreen(
    @StringRes title: Int,
    message: AnnotatedString,
    state: IceBlockState,
    glassContent: GlassContent,
    @StringRes blockDescription: Int,
    @StringRes resultMessage: Int,
    resultAction: () -> Unit,
    onHomeClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    LaunchedEffect(state.isShattered) {
        if (state.isShattered) resultAction()
    }

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
            ) {
                MyNavigationBar(
                    title = title,
                    onHomeClick = onHomeClick,
                )

                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = message,
                    style = DefaultTextStyles.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                IceBlock(
                    state = state,
                    description = stringResource(id = blockDescription),
                    glassContent = glassContent,
                    modifier =
                        Modifier
                            .widthIn(max = 360.dp)
                            .align(Alignment.CenterHorizontally),
                )

                AnimatedVisibility(
                    visible = state.isShattered,
                    enter = fadeIn(),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = resultMessage),
                        style = DefaultTextStyles.body,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            AppButton(
                text = stringResource(id = R.string.continuer),
                onClick = onContinueClick,
                enabled = state.isShattered,
                modifier =
                    Modifier
                        .fillMaxWidth(0.7f)
                        .padding(top = 24.dp)
                        .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview(showBackground = true, name = "Bloc de glace")
@Composable
private fun IceBlockScreenPreview() {
    IceBlockScreen(
        title = R.string.reception_titre,
        message = AnnotatedString("Tapote vite sur le bloc de glace pour le briser !"),
        state = IceBlockState(initialHits = 4),
        glassContent = GlassContent.CrushedIce,
        blockDescription = R.string.reception_five_ice_description,
        resultMessage = R.string.reception_five_ingredient,
        resultAction = {},
        onHomeClick = {},
        onContinueClick = {},
    )
}
