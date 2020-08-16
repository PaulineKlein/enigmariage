package com.pklein.mariage.utils.uiUtils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.utils.SharedPreferenceStored.updateClue

enum class PopinType {
    SUCCESS,
    ERROR,
    CLAPPING,
    CLUE
}

object Alerts {

    fun showAlert(
        context: Context,
        titre: String,
        message: String,
        PositiveButton: String,
        NegativeButton: String,
        onClickButton: (() -> Unit)?
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titre)
        builder.setMessage(message)

        builder.setPositiveButton(PositiveButton) { dialog, _ ->
            onClickButton?.invoke()
            dialog.dismiss()
        }

        builder.setNegativeButton(NegativeButton) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun showPopup(
        context: Context,
        message: String,
        onDismiss: (() -> Unit)?,
        popinType: PopinType
    ) {
        val dialog = Dialog(context)
        val delay = when (popinType) {
            PopinType.SUCCESS -> {
                dialog.setContentView(R.layout.popup_success)
                2500
            }
            PopinType.CLAPPING -> {
                dialog.setContentView(R.layout.popup_clapping)
                3000
            }
            PopinType.ERROR -> {
                dialog.setContentView(R.layout.popup_failure)
                2500
            }
            PopinType.CLUE -> {
                dialog.setContentView(R.layout.popup_clue)
                15000
            }
        }

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.findViewById<TextView>(R.id.tv_popup_title).text = message
        dialog.setOnDismissListener {
            onDismiss?.invoke()
        }
        dialog.show()

        val handler = Handler()
        handler.postDelayed({ dialog.dismiss() }, delay.toLong())
    }

    fun showSuccess(context: Context, onDismiss: (() -> Unit)?) {
        val name = PlayerViewModel.getName()
        showPopup(
            context,
            context.getString(R.string.bonne_reponse, name),
            onDismiss,
            PopinType.SUCCESS
        )
    }

    fun showError(context: Context) {
        showPopup(
            context,
            context.getString(R.string.erreur),
            null,
            PopinType.ERROR
        )
    }

    fun showClue(context: Context, message: String) {
        showPopup(
            context,
            message,
            ::updateClue,
            PopinType.CLUE
        )
    }
}