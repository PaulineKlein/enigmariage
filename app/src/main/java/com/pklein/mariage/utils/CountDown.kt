package com.pklein.mariage.utils

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData

const val TIMER_MSEC_BEFORE_ERROR: Long = 360000 // 5s 5000 et 5 min 300000, 6 min 360000 et 10 min 600000
const val TIMER_MSEC_COUNTDOWN_INTERVAL: Long = 1000

object CountDown : CountDownTimer(TIMER_MSEC_BEFORE_ERROR, TIMER_MSEC_COUNTDOWN_INTERVAL) {

    var updateNbOfCountDown = MutableLiveData<EventLiveData<Int>>()

    override fun onTick(millisUntilFinished: Long) {
        // nothing to do
    }

    override fun onFinish() {
        var actualNbOfCountDown =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.COUNT_DOWN)
        if (actualNbOfCountDown.isNullOrEmpty()) actualNbOfCountDown = "0"

        updateNbOfCountDown.value = EventLiveData(actualNbOfCountDown.toInt())
    }
}