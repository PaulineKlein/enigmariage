package com.pklein.mariage.presentation.introduction.screen

import android.view.HapticFeedbackConstants
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pklein.mariage.R
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.modifier.clearFocusOnTap
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorAccent
import com.pklein.mariage.ui.theme.ColorHelp
import com.pklein.mariage.ui.theme.ColorHelpFonce
import com.pklein.mariage.ui.theme.ColorHelpText
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.annotatedStringResource

private val CONTENT_MAX_WIDTH = 480.dp
private const val NAME_MAX_LENGTH = 10
private val SECTION_SPACING = 24.dp
private val CARD_CORNER_RADIUS = 16.dp
private val CARD_SPACING = 16.dp
private val BADGE_SIZE = 28.dp
private const val CARD_SELECTED_SCALE = 1.04f
private const val CARD_IDLE_ALPHA = 0.7f
private const val GENDER_IMAGE_RATIO = 1f
private const val CONTINUE_BUTTON_WIDTH_RATIO = 0.7f

private data class GenderChoice(
    val gender: PlayerGender,
    @DrawableRes val image: Int,
    @StringRes val label: Int,
)

private val genderChoices =
    listOf(
        GenderChoice(PlayerGender.DOG, R.drawable.image_perso_dog, R.string.add_player_gender_dog),
        GenderChoice(
            PlayerGender.RABBIT,
            R.drawable.image_perso_rabbit,
            R.string.add_player_gender_rabbit,
        ),
    )

/** Pick a name and an inspector (dog or rabbit), then continue; replaces activity_add_player.xml. */
@Composable
fun AddPlayerScreen(onContinueClick: (name: String, gender: PlayerGender) -> Unit) {
    val nameState = rememberTextFieldState()
    var gender by rememberSaveable { mutableStateOf<PlayerGender?>(null) }

    AddPlayerContent(
        nameState = nameState,
        gender = gender,
        onGenderChange = { gender = it },
        onContinueClick = { gender?.let { onContinueClick(nameState.text.toString(), it) } },
    )
}

@Composable
private fun AddPlayerContent(
    nameState: TextFieldState,
    gender: PlayerGender?,
    onGenderChange: (PlayerGender) -> Unit,
    onContinueClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    AppTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clearFocusOnTap()
                    .safeDrawingPadding()
                    .padding(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(modifier = Modifier.widthIn(max = CONTENT_MAX_WIDTH)) {
                    Text(
                        text = stringResource(R.string.add_player_titre),
                        style = DefaultTextStyles.titre,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    SectionTitle(
                        text = stringResource(R.string.add_player_name),
                        modifier = Modifier.padding(top = SECTION_SPACING),
                    )
                    CustomInput(
                        state = nameState,
                        hint = stringResource(R.string.add_player_name_hint),
                        inputTransformation = InputTransformation.maxLength(NAME_MAX_LENGTH),
                        onKeyboardAction = { focusManager.clearFocus() },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                    )

                    SectionTitle(
                        text = stringResource(R.string.add_player_gender),
                        modifier = Modifier.padding(top = SECTION_SPACING),
                    )
                    SectionMessage(text = stringResource(R.string.add_player_gender_message))
                    GendersRow(
                        selected = gender,
                        onSelect = onGenderChange,
                        modifier = Modifier.padding(top = 16.dp),
                    )

                    SectionTitle(
                        text = stringResource(R.string.add_player_save_auto_title),
                        modifier = Modifier.padding(top = SECTION_SPACING),
                    )
                    SectionMessage(text = stringResource(R.string.add_player_save_auto_message))

                    ClueCard(modifier = Modifier.padding(top = 16.dp))
                }
            }

            AppButton(
                text = stringResource(R.string.continuer),
                onClick = onContinueClick,
                enabled = nameState.text.isNotEmpty() && gender != null,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = CONTENT_MAX_WIDTH)
                        .fillMaxWidth(CONTINUE_BUTTON_WIDTH_RATIO),
            )
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = DefaultTextStyles.sousTitre,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun SectionMessage(text: String) {
    Text(
        text = text,
        style = DefaultTextStyles.body,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
    )
}

@Composable
private fun GendersRow(
    selected: PlayerGender?,
    onSelect: (PlayerGender) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(CARD_SPACING),
    ) {
        genderChoices.forEach { choice ->
            GenderCard(
                choice = choice,
                isSelected = selected == choice.gender,
                onClick = { onSelect(choice.gender) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun GenderCard(
    choice: GenderChoice,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val view = LocalView.current
    val scale by animateFloatAsState(
        targetValue = if (isSelected) CARD_SELECTED_SCALE else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "genderScale",
    )
    val shape = RoundedCornerShape(CARD_CORNER_RADIUS)

    Box(
        modifier =
            modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .then(if (isSelected) Modifier.shadow(6.dp, shape) else Modifier)
                    .clip(shape)
                    .background(Color.White.copy(alpha = if (isSelected) 1f else CARD_IDLE_ALPHA))
                    .border(
                        border =
                            if (isSelected) {
                                BorderStroke(3.dp, ColorAccent)
                            } else {
                                BorderStroke(1.dp, Color.White)
                            },
                        shape = shape,
                    ).selectable(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            onClick()
                        },
                        role = Role.RadioButton,
                    ).padding(8.dp),
        ) {
            Image(
                painter = painterResource(choice.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(GENDER_IMAGE_RATIO)
                        .clip(RoundedCornerShape(8.dp)),
            )
            Text(
                text = stringResource(choice.label),
                style = DefaultTextStyles.body.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
            )
        }
        if (isSelected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                        .size(BADGE_SIZE)
                        .background(ColorAccent, CircleShape)
                        .clearAndSetSemantics {},
            ) {
                Text(
                    text = "✓",
                    style =
                        DefaultTextStyles.bodyBig.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                )
            }
        }
    }
}

@Composable
private fun ClueCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorHelp),
        border = BorderStroke(2.dp, ColorHelpFonce),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.add_player_clue),
                style = DefaultTextStyles.sousTitre.copy(color = ColorHelpText, fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = annotatedStringResource(R.string.indice_explication),
                style = DefaultTextStyles.body,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.clearAndSetSemantics {},
            ) {
                Text(
                    text = stringResource(R.string.indice),
                    style =
                        DefaultTextStyles.body.copy(
                            color = ColorHelpText,
                            letterSpacing = 0.4.sp,
                        ),
                )
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ColorHelpText),
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Add player - empty")
@Composable
private fun AddPlayerScreenEmptyPreview() {
    AddPlayerContent(
        nameState = rememberTextFieldState(),
        gender = null,
        onGenderChange = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Add player - rabbit selected")
@Composable
private fun AddPlayerScreenFilledPreview() {
    AddPlayerContent(
        nameState = rememberTextFieldState("Pauline"),
        gender = PlayerGender.RABBIT,
        onGenderChange = {},
        onContinueClick = {},
    )
}

@Preview(
    showSystemUi = true,
    name = "Add player - landscape",
    device = "spec:width=891dp,height=411dp",
)
@Composable
private fun AddPlayerScreenLandscapePreview() {
    AddPlayerContent(
        nameState = rememberTextFieldState("Pauline"),
        gender = PlayerGender.DOG,
        onGenderChange = {},
        onContinueClick = {},
    )
}

@Preview(showSystemUi = true, name = "Add player - interactive")
@Composable
private fun AddPlayerScreenInteractivePreview() {
    AddPlayerScreen(onContinueClick = { _, _ -> })
}
