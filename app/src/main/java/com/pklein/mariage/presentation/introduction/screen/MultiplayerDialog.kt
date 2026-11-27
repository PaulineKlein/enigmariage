package com.pklein.mariage.presentation.introduction.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pklein.mariage.R
import com.pklein.mariage.data.model.GAME_NAME_MAX_LENGTH
import com.pklein.mariage.data.model.GameNames
import com.pklein.mariage.data.model.ROOM_CODE_MIN_LENGTH
import com.pklein.mariage.data.model.generateRoomCode
import com.pklein.mariage.data.model.toRoomCode
import com.pklein.mariage.data.repository.RoomCheck
import com.pklein.mariage.data.repository.RoomResult
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.modifier.clearFocusOnTap
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorErreur
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.WindowBackgroundBrush
import kotlinx.coroutines.launch

private val DIALOG_MAX_WIDTH = 400.dp
private val DIALOG_CORNER_RADIUS = 24.dp
private val DIALOG_PADDING = 24.dp
private val CODE_LETTER_SPACING = 6.sp

private enum class MultiplayerStep { CHOICE, CREATE, JOIN, HELP }

private val RoomCodeInput =
    InputTransformation {
        val typed = asCharSequence().toString()
        val code = typed.toRoomCode()
        if (code != typed) replace(0, length, code)
    }

/**
 * Pick "create a room" (shows a fresh code to share) or "join a room" (type the host's code). The room is created or
 * looked up online first, and only an [RoomResult.OK] calls [onStart] with the code. The host can also rename the
 * couple and the villain; a joining player gets the host's names back from [onJoinRoom]. With a saved game
 * ([hasSavedGame]), the player picks between bringing it into the room or starting a new one; [onStart] says which.
 */
@Composable
fun MultiplayerDialog(
    onCreateRoom: suspend (roomCode: String, names: GameNames) -> RoomResult,
    onJoinRoom: suspend (roomCode: String) -> RoomCheck,
    onStart: (roomCode: String, names: GameNames, keepProgress: Boolean) -> Unit,
    onDismiss: () -> Unit,
    hasSavedGame: Boolean = false,
) {
    var keepProgress by rememberSaveable { mutableStateOf(true) }
    var step by rememberSaveable { mutableStateOf(MultiplayerStep.CHOICE) }
    val createdCode = rememberSaveable { generateRoomCode() }
    val joinCodeState = rememberTextFieldState()
    val brideState = rememberTextFieldState()
    val groomState = rememberTextFieldState()
    val villainState = rememberTextFieldState()
    var checking by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<RoomResult?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(joinCodeState) {
        snapshotFlow { joinCodeState.text.toString() }.collect { error = null }
    }

    fun check(
        code: String,
        action: suspend () -> RoomCheck,
    ) {
        checking = true
        error = null
        scope.launch {
            val room = action()
            checking = false
            if (room.result == RoomResult.OK) {
                onStart(
                    code,
                    room.names,
                    !hasSavedGame || keepProgress,
                )
            } else {
                error = room.result
            }
        }
    }

    fun goTo(newStep: MultiplayerStep) {
        error = null
        step = newStep
    }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = DIALOG_MAX_WIDTH)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(DIALOG_CORNER_RADIUS))
                    .background(WindowBackgroundBrush)
                    .clearFocusOnTap()
                    .verticalScroll(rememberScrollState())
                    .padding(DIALOG_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (step) {
                MultiplayerStep.CHOICE -> {
                    ChoiceContent(
                        hasSavedGame = hasSavedGame,
                        keepProgress = keepProgress,
                        onKeepProgressChange = { keepProgress = it },
                        onCreate = { goTo(MultiplayerStep.CREATE) },
                        onJoin = { goTo(MultiplayerStep.JOIN) },
                        onHelp = { goTo(MultiplayerStep.HELP) },
                        onCancel = onDismiss,
                    )
                }

                MultiplayerStep.CREATE -> {
                    CreateContent(
                        code = createdCode,
                        brideState = brideState,
                        groomState = groomState,
                        villainState = villainState,
                        checking = checking,
                        error = error,
                        onStart = {
                            val names =
                                GameNames.of(
                                    brideState.text.toString(),
                                    groomState.text.toString(),
                                    villainState.text.toString(),
                                )
                            check(createdCode) {
                                RoomCheck(
                                    onCreateRoom(createdCode, names),
                                    names,
                                )
                            }
                        },
                        onBack = { goTo(MultiplayerStep.CHOICE) },
                    )
                }

                MultiplayerStep.HELP -> {
                    HelpContent(onBack = { goTo(MultiplayerStep.CHOICE) })
                }

                MultiplayerStep.JOIN -> {
                    JoinContent(
                        codeState = joinCodeState,
                        checking = checking,
                        error = error,
                        onJoin = {
                            val code = joinCodeState.text.toString()
                            check(code) { onJoinRoom(code) }
                        },
                        onBack = { goTo(MultiplayerStep.CHOICE) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceContent(
    hasSavedGame: Boolean,
    keepProgress: Boolean,
    onKeepProgressChange: (Boolean) -> Unit,
    onCreate: () -> Unit,
    onJoin: () -> Unit,
    onHelp: () -> Unit,
    onCancel: () -> Unit,
) {
    DialogTitle(R.string.multiplayer_title)
    DialogMessage(stringResource(R.string.multiplayer_message))
    if (hasSavedGame) {
        ProgressChoice(
            R.string.multiplayer_keep_game,
            selected = keepProgress,
        ) { onKeepProgressChange(true) }
        ProgressChoice(
            R.string.multiplayer_new_game,
            selected = !keepProgress,
        ) { onKeepProgressChange(false) }
    }
    AppButton(
        text = stringResource(R.string.multiplayer_create),
        onClick = onCreate,
        modifier = Modifier.fillMaxWidth(),
    )
    AppButton(
        text = stringResource(R.string.multiplayer_join),
        onClick = onJoin,
        modifier = Modifier.fillMaxWidth(),
    )
    SecondaryAction(text = stringResource(R.string.multiplayer_help), onClick = onHelp)
    SecondaryAction(text = stringResource(R.string.multiplayer_cancel), onClick = onCancel)
}

/** Who does what in a room: the organizer creates it and picks the names, the others join with its code. */
@Composable
private fun HelpContent(onBack: () -> Unit) {
    DialogTitle(R.string.multiplayer_help)
    DialogMessage(stringResource(R.string.multiplayer_help_step_1))
    DialogMessage(stringResource(R.string.multiplayer_help_step_2))
    DialogMessage(stringResource(R.string.multiplayer_help_step_3))
    DialogMessage(stringResource(R.string.multiplayer_help_saved_game))
    AppButton(
        text = stringResource(R.string.multiplayer_help_ok),
        onClick = onBack,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ProgressChoice(
    @StringRes text: Int,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .selectable(selected = selected, onClick = onSelect, role = Role.RadioButton),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(
            text = stringResource(text),
            style = DefaultTextStyles.body,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@Composable
private fun CreateContent(
    code: String,
    brideState: TextFieldState,
    groomState: TextFieldState,
    villainState: TextFieldState,
    checking: Boolean,
    error: RoomResult?,
    onStart: () -> Unit,
    onBack: () -> Unit,
) {
    DialogTitle(R.string.multiplayer_create)
    DialogMessage(stringResource(R.string.multiplayer_create_message))
    val context = LocalContext.current
    val codeDescription =
        stringResource(
            R.string.multiplayer_create_code_accessibility,
            code.toList().joinToString(" "),
        )
    Text(
        text = code,
        style = DefaultTextStyles.sousTitre.copy(letterSpacing = CODE_LETTER_SPACING),
        textAlign = TextAlign.Center,
        modifier =
            Modifier
                .combinedClickable(onClick = {}, onLongClick = { copyCode(context, code) })
                .clearAndSetSemantics { contentDescription = codeDescription },
    )
    AppButton(
        text = stringResource(R.string.multiplayer_share),
        onClick = { shareCode(context, code) },
        modifier = Modifier.fillMaxWidth(),
    )
    DialogMessage(stringResource(R.string.multiplayer_names_message))
    NameInput(brideState, stringResource(R.string.multiplayer_bride_hint))
    NameInput(groomState, stringResource(R.string.multiplayer_groom_hint))
    NameInput(villainState, stringResource(R.string.multiplayer_villain_hint), isLast = true)
    CheckStatus(checking = checking, error = error)
    AppButton(
        text = stringResource(R.string.multiplayer_start),
        onClick = onStart,
        enabled = !checking,
        modifier = Modifier.fillMaxWidth(),
    )
    SecondaryAction(text = stringResource(R.string.multiplayer_back), onClick = onBack)
}

@Composable
private fun NameInput(
    state: TextFieldState,
    hint: String,
    isLast: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    CustomInput(
        state = state,
        hint = hint,
        imeAction = if (isLast) ImeAction.Done else ImeAction.Next,
        inputTransformation = InputTransformation.maxLength(GAME_NAME_MAX_LENGTH),
        onKeyboardAction = {
            if (isLast) focusManager.clearFocus() else focusManager.moveFocus(FocusDirection.Next)
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

private fun copyCode(
    context: Context,
    code: String,
) {
    val clipboard = context.getSystemService(ClipboardManager::class.java)
    clipboard.setPrimaryClip(
        ClipData.newPlainText(
            context.getString(R.string.multiplayer_title),
            code,
        ),
    )
    // Android 13+ already confirms a copy on its own.
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        Toast.makeText(context, R.string.multiplayer_copied, Toast.LENGTH_SHORT).show()
    }
}

private fun shareCode(
    context: Context,
    code: String,
) {
    val intent =
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.multiplayer_share_text, code))
        }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.multiplayer_share_title),
        ),
    )
}

@Composable
private fun JoinContent(
    codeState: TextFieldState,
    checking: Boolean,
    error: RoomResult?,
    onJoin: () -> Unit,
    onBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    DialogTitle(R.string.multiplayer_join)
    DialogMessage(stringResource(R.string.multiplayer_join_message))
    CustomInput(
        state = codeState,
        hint = stringResource(R.string.multiplayer_join_hint),
        inputTransformation = RoomCodeInput,
        onKeyboardAction = { focusManager.clearFocus() },
        modifier = Modifier.fillMaxWidth(),
    )
    CheckStatus(checking = checking, error = error)
    AppButton(
        text = stringResource(R.string.multiplayer_join_action),
        onClick = onJoin,
        enabled = codeState.text.length >= ROOM_CODE_MIN_LENGTH && !checking,
        modifier = Modifier.fillMaxWidth(),
    )
    SecondaryAction(text = stringResource(R.string.multiplayer_back), onClick = onBack)
}

/** "Vérification…" while the room is being created or looked up, then why it failed. */
@Composable
private fun CheckStatus(
    checking: Boolean,
    error: RoomResult?,
) {
    when {
        checking -> {
            Text(
                text = stringResource(R.string.multiplayer_checking),
                style = DefaultTextStyles.body,
                textAlign = TextAlign.Center,
            )
        }

        error == RoomResult.NOT_FOUND -> {
            ErrorMessage(R.string.multiplayer_error_not_found)
        }

        error == RoomResult.UNAVAILABLE -> {
            ErrorMessage(R.string.multiplayer_error_unavailable)
        }

        error == RoomResult.RATE_LIMITED -> {
            ErrorMessage(R.string.multiplayer_error_rate_limited)
        }
    }
}

@Composable
private fun ErrorMessage(
    @StringRes text: Int,
) {
    Text(
        text = stringResource(text),
        style = DefaultTextStyles.body.copy(color = ColorErreur),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DialogTitle(
    @StringRes text: Int,
) {
    Text(
        text = stringResource(text),
        style = DefaultTextStyles.sousTitre,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun DialogMessage(text: String) {
    Text(
        text = text,
        style = DefaultTextStyles.body,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun SecondaryAction(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(text = text, style = DefaultTextStyles.bodyLink)
    }
}

@Preview(showBackground = true, name = "Multijoueur")
@Composable
private fun MultiplayerDialogPreview() {
    AppTheme {
        MultiplayerDialog(
            onCreateRoom = { _, _ -> RoomResult.OK },
            onJoinRoom = { RoomCheck(RoomResult.OK) },
            onStart = { _, _, _ -> },
            onDismiss = {},
        )
    }
}
