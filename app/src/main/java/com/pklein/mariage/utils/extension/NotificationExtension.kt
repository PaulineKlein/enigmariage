package com.pklein.mariage.utils.extension

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import java.util.concurrent.atomic.AtomicInteger

fun NotificationManager.sendNotification(
    title: String,
    message: String,
    context: Context,
    intentToCall: Intent? = null
) {

    createChannel(
        context.getString(R.string.notification_channel_id),
        context.getString(R.string.app_name),
        context
    )

    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_notification)
        .setColor(ContextCompat.getColor(context, R.color.colorAccent))
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    val id = NotificationID.id
    if (intentToCall != null) {
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            id,
            intentToCall,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setContentIntent(contentPendingIntent)
    }

    notify(id, builder.build())
}

private fun createChannel(channelId: String, channelName: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(false)
            }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = context.getString(R.string.app_name)

        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}

internal object NotificationID {
    private val c = AtomicInteger(0)
    val id: Int
        get() = c.incrementAndGet()
}