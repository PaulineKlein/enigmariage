package com.pklein.mariage.utils

import java.util.Date
import java.util.concurrent.TimeUnit

fun calculateTimeDifference(startTime: String, endTime: String): String? =
    try {
        val diff = Date(endTime.toLong()).time - Date(startTime.toLong()).time

        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60

        buildString {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0 || hours > 0) append("${minutes}min ")
            append("${seconds}s")
        }
    } catch (_: NumberFormatException) {
        null
    }

fun currentTimeToString(): String = System.currentTimeMillis().toString()
