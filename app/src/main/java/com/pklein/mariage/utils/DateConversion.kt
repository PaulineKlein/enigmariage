package com.pklein.mariage.utils

import java.util.Date
import java.util.concurrent.TimeUnit

fun calculateTimeDifference(
    startTime: String,
    endTime: String,
): String? =
    try {
        formatDuration(Date(endTime.toLong()).time - Date(startTime.toLong()).time)
    } catch (_: NumberFormatException) {
        null
    }

fun formatDuration(millis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0 || hours > 0) append("${minutes}min ")
        append("${seconds}s")
    }
}

fun currentTimeToString(): String = System.currentTimeMillis().toString()
