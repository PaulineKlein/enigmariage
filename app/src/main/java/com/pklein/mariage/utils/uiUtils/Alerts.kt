package com.pklein.mariage.utils.uiUtils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.utils.SharedPreferenceStored.updateClue

enum class PopinType {
    SUCCESS,
    UNLOCK_SUCCESS,
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
        popinType: PopinType,
        gender: PLAYER_GENDER? = null
    ) {
        val dialog = Dialog(context)
        val delay = when (popinType) {
            PopinType.SUCCESS -> {
                dialog.setContentView(R.layout.popup_success)
                2500
            }
            PopinType.UNLOCK_SUCCESS -> {
                dialog.setContentView(R.layout.popup_unlock_success)
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
        gender?.let {
            if (it == PLAYER_GENDER.FEMALE)
                dialog.findViewById<AppCompatImageView>(R.id.iv_user)
                    ?.setImageResource(R.drawable.image_zelda_rond)
            else
                dialog.findViewById<AppCompatImageView>(R.id.iv_user)
                    ?.setImageResource(R.drawable.image_link_rond)
        }
        dialog.setOnDismissListener {
            onDismiss?.invoke()
        }
        dialog.show()

        val handler = Handler()
        handler.postDelayed({ dialog.dismiss() }, delay.toLong())
    }

    fun showSuccess(
        context: Context,
        onDismiss: (() -> Unit)?,
        popinType: PopinType = PopinType.SUCCESS
    ) {
        val name = PlayerViewModel.getName()
        val genderStr = PlayerViewModel.getGender()
        val gender =
            if (genderStr == PLAYER_GENDER.FEMALE.name) PLAYER_GENDER.FEMALE else PLAYER_GENDER.MALE

        showPopup(
            context,
            context.getString(R.string.bonne_reponse, name),
            onDismiss,
            popinType,
            gender
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