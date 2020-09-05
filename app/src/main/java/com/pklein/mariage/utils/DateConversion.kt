package com.pklein.mariage.utils

import java.util.*
import java.util.concurrent.TimeUnit

fun calculateTimeDifference(startTime: String, endTime: String): String {
    val date1 = Date(startTime.toLong())
    val date2 = Date(endTime.toLong())
    val diff: Long = date2.time - date1.time

    return String.format(
        "%02dh %02dmin %02ds",
        TimeUnit.MILLISECONDS.toHours(diff),
        TimeUnit.MILLISECONDS.toMinutes(diff) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
        TimeUnit.MILLISECONDS.toSeconds(diff) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff))
    )
}

fun currentTimeToString(): String {
    return System.currentTimeMillis().toString()
}
