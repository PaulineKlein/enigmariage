package com.pklein.mariage.presentation.resultat

import androidx.lifecycle.ViewModel
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.calculateTimeDifference

class ResultsViewModel : ViewModel() {

    fun calculateScore(): String {
        var actualNbOfError =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_ERRORS)
        var actualNbOfClue =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_CLUES)

        if (actualNbOfError.isNullOrEmpty()) actualNbOfError = "0"
        if (actualNbOfClue.isNullOrEmpty()) actualNbOfClue = "0"

        val score = 100 - actualNbOfError.toInt() - (5 * actualNbOfClue.toInt())
        return "$score / 100"
    }

    fun calculateTime(): String? {
        val startTimeStr = SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_START_TIME)
        val endTimeStr = SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_END_TIME)

        return if (!startTimeStr.isNullOrEmpty() && !endTimeStr.isNullOrEmpty()) {
            calculateTimeDifference(startTimeStr, endTimeStr)
        } else {
            null
        }
    }
}