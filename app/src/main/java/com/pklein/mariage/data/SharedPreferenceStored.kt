package com.pklein.mariage.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.pklein.mariage.MariageApplication
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies

object SharedPreferenceStored {
    private const val SHARED_PREFERENCE_FILE = "mariage-shared-preference"
    private const val CHECK_SHOWN_PREFIX = "check_shown_"
    private val preferences: SharedPreferences by lazy {
        MariageApplication
            .getAppContext()
            .getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

    fun getValue(key: SharedPreferenceKey): String? = preferences.getString(key.name, "")

    fun storeValue(
        key: SharedPreferenceKey,
        value: String?,
    ) {
        preferences.edit {
            putString(key.name, value)
        }
    }

    fun getTrophy(key: Trophies): Boolean = preferences.getBoolean(key.name, false)

    fun saveTrophy(key: Trophies) = preferences.edit { putBoolean(key.name, true) }

    fun isCheckShown(id: String): Boolean = preferences.getBoolean(CHECK_SHOWN_PREFIX + id, false)

    fun markCheckShown(id: String) = preferences.edit { putBoolean(CHECK_SHOWN_PREFIX + id, true) }

    fun resetPreference() {
        SharedPreferenceKey.entries.forEach { storeValue(it, null) }
        preferences.edit {
            preferences.all.keys
                .filter { it.startsWith(CHECK_SHOWN_PREFIX) }
                .forEach { remove(it) }
        }
    }
}
