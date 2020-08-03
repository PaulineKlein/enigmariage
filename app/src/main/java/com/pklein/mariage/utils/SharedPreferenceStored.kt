package com.pklein.mariage.utils

import android.content.Context
import com.pklein.mariage.MariageApplication

enum class SHARED_PREFERENCE_KEY {
    PLAYER_NAME,
    PLAYER_GENDER,
    PLAYER_PAGE
}

object SharedPreferenceStored {

    const val SHARED_PREFERENCE_FILE = "mariage-shared-preference"

    fun getValue(key: SHARED_PREFERENCE_KEY): String? {
        val preferences = MariageApplication.getAppContext()
            .getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        return preferences.getString(key.name, "") ?: null
    }

    fun storeValue(key: SHARED_PREFERENCE_KEY, value: String?) {
        val preferences = MariageApplication.getAppContext()
            .getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key.name, value)
        editor.apply()
    }
}