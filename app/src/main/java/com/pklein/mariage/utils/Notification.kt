package com.pklein.mariage.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity
import com.pklein.mariage.presentation.localisationBateau.LocalisationBateauActivity
import com.pklein.mariage.utils.extension.sendNotification

object Notification {
    fun sendNotificationSalleEmbarquement(context: Context) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val intent = Intent(context, LocalisationBateauActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(LocalisationBateauActivity.EXTRA_NAVIGATE_TO_EMBARQUEMENT_THREE, true)
        }

        notificationManager.sendNotification(
            context.getString(R.string.embarquement_two_notification_titre),
            context.getString(R.string.embarquement_two_notification_message),
            context,
            intent
        )
    }

    fun sendNotificationCombatFinal(context: Context) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val intent = Intent(context, CombatFinalActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(SplashCoordinator.EXTRA_STEP, GameStep.Result.key)
            putExtra(CombatFinalActivity.EXTRA_USELESS_TROPHY, true)
        }

        notificationManager.sendNotification(
            context.getString(R.string.combatFinal_one_notification_titre),
            context.getString(R.string.combatFinal_one_notification_message),
            context,
            intent
        )
    }

    fun cancelNotification(context: Context) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancelAll()
    }
}
