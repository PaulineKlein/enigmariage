package com.pklein.mariage.utils

import android.content.Context
import com.pklein.mariage.MariageApplication

enum class SHARED_PREFERENCE_KEY {
    PLAYER_NAME,
    PLAYER_GENDER,
    PLAYER_PAGE,
    PLAYER_START_TIME,
    PLAYER_END_TIME,
    PLAYER_CLUES,
    PLAYER_ERRORS,
    COUNT_DOWN,
    UNIVERS_1,
    UNIVERS_2_COCKTAILS,
    UNIVERS_2_CANADA,
    UNIVERS_2_JAPON,
    UNIVERS_2_CAMBODGE,
    UNIVERS_3
}

object SharedPreferenceStored {

    private const val SHARED_PREFERENCE_FILE = "mariage-shared-preference"
    private val preferences = MariageApplication.getAppContext()
        .getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)

    fun getValue(key: SHARED_PREFERENCE_KEY): String? {
        return preferences.getString(key.name, "")
    }

    fun storeValue(key: SHARED_PREFERENCE_KEY, value: String?) {
        val editor = preferences.edit()
        editor.putString(key.name, value)
        editor.apply()
    }

    fun updateClue() {
        val actualNbOfClue = preferences.getString(SHARED_PREFERENCE_KEY.PLAYER_CLUES.name, "")
        val newNbOfCLue = if (actualNbOfClue.isNullOrEmpty()) {
            1
        } else {
            actualNbOfClue.toInt() + 1
        }
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_CLUES, newNbOfCLue.toString())
    }

    fun updateError() {
        val actualNbOfError = preferences.getString(SHARED_PREFERENCE_KEY.PLAYER_ERRORS.name, "")
        val newNbOfError = if (actualNbOfError.isNullOrEmpty()) {
            1
        } else {
            actualNbOfError.toInt() + 1
        }
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_ERRORS, newNbOfError.toString())
    }

    fun updateCountDown() {
        val actualNbOfCountDown = preferences.getString(SHARED_PREFERENCE_KEY.COUNT_DOWN.name, "")
        val newNbOfCountDown = if (actualNbOfCountDown.isNullOrEmpty()) {
            1
        } else {
            actualNbOfCountDown.toInt() + 1
        }
        storeValue(SHARED_PREFERENCE_KEY.COUNT_DOWN, newNbOfCountDown.toString())
    }


    fun resetPreference() {
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_NAME, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_GENDER, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_PAGE, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_START_TIME, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_END_TIME, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_CLUES, null)
        storeValue(SHARED_PREFERENCE_KEY.PLAYER_ERRORS, null)
        storeValue(SHARED_PREFERENCE_KEY.COUNT_DOWN, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_1, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_2_CAMBODGE, null)
        storeValue(SHARED_PREFERENCE_KEY.UNIVERS_3, null)
    }
}