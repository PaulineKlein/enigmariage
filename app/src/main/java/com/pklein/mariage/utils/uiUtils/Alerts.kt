package com.pklein.mariage.utils.uiUtils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.HapticFeedbackConstants
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toDrawable
import com.pklein.mariage.R
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.utils.CountDown

enum class PopinType {
    SUCCESS,
    UNLOCK_SUCCESS,
    FINAL_SUCCESS,
    ERROR,
    CLAPPING,
    CLUE,
}

object Alerts {
    private var isPopupShown = false

    fun showAlert(
        context: Context,
        titre: String,
        message: String,
        positiveButton: String,
        negativeButton: String,
        onClickButton: (() -> Unit)?,
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_custom_confirm)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        dialog.findViewById<TextView>(R.id.tv_dialog_title).text = titre
        dialog.findViewById<TextView>(R.id.tv_dialog_message).text = message
        dialog.findViewById<AppCompatButton>(R.id.btn_dialog_positive).apply {
            text = positiveButton
            setOnClickListener {
                onClickButton?.invoke()
                dialog.dismiss()
            }
        }
        dialog.findViewById<AppCompatButton>(R.id.btn_dialog_negative).apply {
            text = negativeButton
            setOnClickListener { dialog.dismiss() }
        }
        dialog.show()
    }

    fun showPopup(
        context: Context,
        message: String,
        onDismiss: (() -> Unit)?,
        popinType: PopinType,
        gender: PlayerGender? = null,
    ) {
        val dialog = Dialog(context)
        val delay =
            when (popinType) {
                PopinType.SUCCESS -> {
                    dialog.setContentView(R.layout.popup_success)
                    3000
                }

                PopinType.UNLOCK_SUCCESS -> {
                    dialog.setContentView(R.layout.popup_unlock_success)
                    3000
                }

                PopinType.FINAL_SUCCESS -> {
                    dialog.setContentView(R.layout.popup_final_success)
                    6000
                }

                PopinType.CLAPPING -> {
                    dialog.setContentView(R.layout.popup_clapping)
                    6000
                }

                PopinType.ERROR -> {
                    dialog.setContentView(R.layout.popup_failure)
                    3000
                }

                PopinType.CLUE -> {
                    dialog.setContentView(R.layout.popup_clue)
                    20000
                }
            }

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.findViewById<TextView>(R.id.tv_popup_title).text = message
        gender?.let {
            if (it == PlayerGender.RABBIT) {
                dialog
                    .findViewById<AppCompatImageView>(R.id.iv_user)
                    ?.setImageResource(R.drawable.image_perso_rabbit)
            } else {
                dialog
                    .findViewById<AppCompatImageView>(R.id.iv_user)
                    ?.setImageResource(R.drawable.image_perso_dog)
            }
        }
        dialog.setOnDismissListener {
            onDismiss?.invoke()
            isPopupShown = false
        }
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.show()
        isPopupShown = true

        if (popinType == PopinType.ERROR) {
            val feedbackConstant =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    HapticFeedbackConstants.REJECT
                } else {
                    HapticFeedbackConstants.LONG_PRESS
                }
            val decorView = dialog.window?.decorView
            decorView?.post {
                decorView.performHapticFeedback(
                    feedbackConstant,
                    HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING,
                )
            }
        }

        val handler = Handler()
        handler.postDelayed({ dialog.dismiss() }, delay.toLong())
    }

    fun showSuccess(
        context: Context,
        name: String?,
        gender: PlayerGender,
        onDismiss: (() -> Unit)?,
        popinType: PopinType = PopinType.SUCCESS,
    ) {
        showPopup(
            context,
            context.getString(R.string.bonne_reponse, name),
            onDismiss,
            popinType,
            gender,
        )
    }

    fun showError(
        context: Context,
        onDismiss: (() -> Unit),
    ) {
        showPopup(context, context.getString(R.string.erreur), onDismiss, PopinType.ERROR)
    }

    fun showInternalError(context: Context) {
        showPopup(
            context,
            context.getString(R.string.internal_erreur),
            null,
            PopinType.ERROR,
        )
    }

    fun showClue(
        context: Context,
        message: String,
        onDismiss: (() -> Unit),
    ) {
        showPopup(context, message, onDismiss, PopinType.CLUE)
    }

    fun showTrophyUnlocked(
        context: Context,
        message: String,
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_trophy)
        dialog.window?.apply {
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            attributes?.windowAnimations = R.style.NotificationAnimation
            setGravity(android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL)
            clearFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            addFlags(android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            addFlags(android.view.WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.findViewById<TextView>(R.id.tv_trophy_message).text = message
        dialog.window?.apply {
            setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            val params = attributes
            params.y = context.resources.getDimensionPixelSize(R.dimen.margin_16)
            attributes = params
            decorView
                .findViewById<android.view.View>(android.R.id.content)
                ?.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()
        isPopupShown = true
        Handler().postDelayed({ dialog.dismiss() }, 6000)
    }

    fun showCountDown(
        context: Context,
        step: Int,
        onCountDownUpdated: (() -> Unit),
    ) {
        if (step < 12) { // les photos vont jusqu'au chiffre 11 :
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.popup_countdown)
            dialog.window?.apply {
                setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                attributes?.windowAnimations = R.style.NotificationAnimation
                setGravity(android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL)
                clearFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                addFlags(android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                addFlags(android.view.WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
            }
            dialog.setCanceledOnTouchOutside(true)

            val countdownStr =
                context
                    .resources
                    .getIdentifier("countdown_$step", "string", context.packageName)

            dialog.findViewById<TextView>(R.id.tv_popup_title).text =
                context.getString(countdownStr)

            dialog.setOnDismissListener {
                if (step < 11) {
                    onCountDownUpdated.invoke()
                    CountDown.start()
                }
            }
            dialog.window?.apply {
                setLayout(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                val params = attributes
                params.y = context.resources.getDimensionPixelSize(R.dimen.margin_16)
                attributes = params
                decorView
                    .findViewById<android.view.View>(android.R.id.content)
                    ?.setOnClickListener { dialog.dismiss() }
            }

            if (isPopupShown) {
                Handler().postDelayed({ dialog.show() }, 10000)
            } else {
                dialog.show()
            }

            Handler().postDelayed({ dialog.dismiss() }, 10000)
        }
    }
}
