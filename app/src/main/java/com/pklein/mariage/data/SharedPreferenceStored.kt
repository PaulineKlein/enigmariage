package com.pklein.mariage.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.pklein.mariage.MariageApplication
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies

object SharedPreferenceStored {
    private const val SHARED_PREFERENCE_FILE = "mariage-shared-preference"
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

    fun resetPreference() {
        SharedPreferenceKey.entries.forEach { storeValue(it, null) }
    }
}
