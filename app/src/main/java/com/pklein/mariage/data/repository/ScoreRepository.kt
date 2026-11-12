package com.pklein.mariage.data.repository

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.SharedPreferenceStored.storeValue
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.utils.currentTimeToString
import javax.inject.Inject

class ScoreRepository
    @Inject
    constructor(
        private val preferences: SharedPreferenceStored,
    ) {
        private val playerErrorStored: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_ERRORS)

        val actualNbOfError: Int
            get() =
                try {
                    playerErrorStored?.toInt() ?: 0
                } catch (_: Exception) {
                    0
                }

        private val playerCluesStored: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_CLUES)

        val actualNbOfClue: Int
            get() =
                try {
                    playerCluesStored?.toInt() ?: 0
                } catch (_: Exception) {
                    0
                }

        val startTimeStr: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_START_TIME)
        val endTimeStr: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_END_TIME)

        fun storeStartTime() {
            preferences.storeValue(SharedPreferenceKey.PLAYER_START_TIME, currentTimeToString())
        }

        fun storeEndTime() {
            preferences.storeValue(SharedPreferenceKey.PLAYER_END_TIME, currentTimeToString())
        }

        fun updateClue() {
            val actualNbOfClue = preferences.getValue(SharedPreferenceKey.PLAYER_CLUES)
            val newNbOfCLue =
                if (actualNbOfClue.isNullOrEmpty()) {
                    1
                } else {
                    actualNbOfClue.toInt() + 1
                }
            storeValue(SharedPreferenceKey.PLAYER_CLUES, newNbOfCLue.toString())
        }

        fun updateError() {
            val actualNbOfError = preferences.getValue(SharedPreferenceKey.PLAYER_ERRORS)
            val newNbOfError =
                if (actualNbOfError.isNullOrEmpty()) {
                    1
                } else {
                    actualNbOfError.toInt() + 1
                }
            storeValue(SharedPreferenceKey.PLAYER_ERRORS, newNbOfError.toString())
        }
    }
