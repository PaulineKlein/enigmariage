package com.pklein.mariage.utils.uiUtils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel

enum class PopinType{
    SUCCESS,
    ERROR,
    CLAPPING
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
        when(popinType){
            PopinType.SUCCESS -> {
                dialog.setContentView(R.layout.popup_success)
            }
            PopinType.CLAPPING -> {
                dialog.setContentView(R.layout.popup_clapping)
            }
            PopinType.ERROR -> {
                dialog.setContentView(R.layout.popup_failure)
            }
        }

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.findViewById<TextView>(R.id.tv_popup_title).text = message
        dialog.setOnDismissListener {
            onDismiss?.invoke()
        }
        dialog.show()

        val handler = Handler()
        handler.postDelayed({ dialog.dismiss() }, 2500)
    }

    fun showSuccess(context: Context, onDismiss: (() -> Unit)?) {
        val name = PlayerViewModel.getName()
        Alerts.showPopup(
            context,
            context.getString(R.string.bonne_reponse, name),
            onDismiss,
            PopinType.SUCCESS
        )
    }

    fun showError(context: Context) {
        Alerts.showPopup(
            context,
            context.getString(R.string.erreur),
            null,
            PopinType.ERROR
        )
    }

}