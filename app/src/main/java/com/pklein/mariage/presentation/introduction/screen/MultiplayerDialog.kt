package com.pklein.mariage.presentation.introduction.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import com.pklein.mariage.R
import com.pklein.mariage.data.model.ROOM_CODE_MIN_LENGTH
import com.pklein.mariage.data.model.generateRoomCode
import com.pklein.mariage.data.model.toRoomCode
import com.pklein.mariage.data.repository.RoomResult
import com.pklein.mariage.ui.atome.AppButton
import com.pklein.mariage.ui.atome.CustomInput
import com.pklein.mariage.ui.modifier.clearFocusOnTap
import com.pklein.mariage.ui.theme.AppTheme
import com.pklein.mariage.ui.theme.ColorErreur
import com.pklein.mariage.ui.theme.DefaultTextStyles
import com.pklein.mariage.ui.theme.WindowBackgroundBrush

private val DIALOG_MAX_WIDTH = 400.dp
private val DIALOG_CORNER_RADIUS = 24.dp
private val DIALOG_PADDING = 24.dp
private val CODE_LETTER_SPACING = 6.sp

private enum class MultiplayerStep { CHOICE, CREATE, JOIN }

private val RoomCodeInput =
    InputTransformation {
        val typed = asCharSequence().toString()
        val code = typed.toRoomCode()
        if (code != typed) replace(0, length, code)
    }

/**
 * Pick "create a room" (shows a fresh code to share) or "join a room" (type the host's code). The room is created or
 * looked up online first, and only an [RoomResult.OK] calls [onStart] with the code.
 */
@Composable
fun MultiplayerDialog(
    onCreateRoom: suspend (roomCode: String) -> RoomResult,
    onJoinRoom: suspend (roomCode: String) -> RoomResult,
    onStart: (roomCode: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var step by rememberSaveable { mutableStateOf(MultiplayerStep.CHOICE) }
    val createdCode = rememberSaveable { generateRoomCode() }
    val joinCodeState = rememberTextFieldState()
    var checking by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<RoomResult?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(joinCodeState) {
        snapshotFlow { joinCodeState.text.toString() }.collect { error = null }
    }

    fun check(
        code: String,
        action: suspend (String) -> RoomResult,
    ) {
        checking = true
        error = null
        scope.launch {
            val result = action(code)
            checking = false
            if (result == RoomResult.OK) onStart(code) else error = result
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
                        onCreate = { goTo(MultiplayerStep.CREATE) },
                        onJoin = { goTo(MultiplayerStep.JOIN) },
                        onCancel = onDismiss,
                    )
                }

                MultiplayerStep.CREATE -> {
                    CreateContent(
                        code = createdCode,
                        checking = checking,
                        error = error,
                        onStart = { check(createdCode, onCreateRoom) },
                        onBack = { goTo(MultiplayerStep.CHOICE) },
                    )
                }

                MultiplayerStep.JOIN -> {
                    JoinContent(
                        codeState = joinCodeState,
                        checking = checking,
                        error = error,
                        onJoin = { check(joinCodeState.text.toString(), onJoinRoom) },
                        onBack = { goTo(MultiplayerStep.CHOICE) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceContent(
    onCreate: () -> Unit,
    onJoin: () -> Unit,
    onCancel: () -> Unit,
) {
    DialogTitle(R.string.multiplayer_title)
    DialogMessage(stringResource(R.string.multiplayer_message))
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
    SecondaryAction(text = stringResource(R.string.multiplayer_cancel), onClick = onCancel)
}

@Composable
private fun CreateContent(
    code: String,
    checking: Boolean,
    error: RoomResult?,
    onStart: () -> Unit,
    onBack: () -> Unit,
) {
    DialogTitle(R.string.multiplayer_create)
    DialogMessage(stringResource(R.string.multiplayer_create_message))
    val codeDescription =
        stringResource(
            R.string.multiplayer_create_code_accessibility,
            code.toList().joinToString(" "),
        )
    Text(
        text = code,
        style = DefaultTextStyles.sousTitre.copy(letterSpacing = CODE_LETTER_SPACING),
        textAlign = TextAlign.Center,
        modifier = Modifier.clearAndSetSemantics { contentDescription = codeDescription },
    )
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppButton(
            text = stringResource(R.string.multiplayer_copy),
            onClick = { copyCode(context, code) },
            modifier = Modifier.weight(1f),
        )
        AppButton(
            text = stringResource(R.string.multiplayer_share),
            onClick = { shareCode(context, code) },
            modifier = Modifier.weight(1f),
        )
    }
    CheckStatus(checking = checking, error = error)
    AppButton(
        text = stringResource(R.string.multiplayer_start),
        onClick = onStart,
        enabled = !checking,
        modifier = Modifier.fillMaxWidth(),
    )
    SecondaryAction(text = stringResource(R.string.multiplayer_back), onClick = onBack)
}

private fun copyCode(
    context: Context,
    code: String,
) {
    val clipboard = context.getSystemService(ClipboardManager::class.java)
    clipboard.setPrimaryClip(ClipData.newPlainText(context.getString(R.string.multiplayer_title), code))
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
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.multiplayer_share_title)))
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

        error == RoomResult.NOT_FOUND -> ErrorMessage(R.string.multiplayer_error_not_found)

        error == RoomResult.UNAVAILABLE -> ErrorMessage(R.string.multiplayer_error_unavailable)

        error == RoomResult.RATE_LIMITED -> ErrorMessage(R.string.multiplayer_error_rate_limited)
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
            onCreateRoom = { RoomResult.OK },
            onJoinRoom = { RoomResult.OK },
            onStart = {},
            onDismiss = {},
        )
    }
}
