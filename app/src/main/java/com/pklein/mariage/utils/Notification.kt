package com.pklein.mariage.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementThreeActivity
import com.pklein.mariage.utils.extension.sendNotification

object Notification {

    fun sendNotificationSalleEmbarquement(context: Context) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val intent = Intent(context, SalleEmbarquementThreeActivity::class.java)

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

        notificationManager.sendNotification(
            context.getString(R.string.combatFinal_one_notification_titre),
            context.getString(R.string.combatFinal_one_notification_message),
            context,
            null
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
