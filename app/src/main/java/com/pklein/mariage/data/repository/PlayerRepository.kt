package com.pklein.mariage.data.repository

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameStep
import javax.inject.Inject

enum class PlayerGender {
    DOG,
    RABBIT,
}

class PlayerRepository
    @Inject
    constructor(
        private val preferences: SharedPreferenceStored,
    ) {
        val name: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_NAME)

        val gender: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_GENDER)

        val page: String?
            get() = preferences.getValue(SharedPreferenceKey.PLAYER_PAGE)

        fun storeName(name: String) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_NAME, name)
        }

        fun storeGender(gender: PlayerGender) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_GENDER, gender.name)
        }

        fun storePage(page: GameStep) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_PAGE, page.name)
        }

        fun resetStorage() {
            preferences.storeValue(SharedPreferenceKey.PLAYER_PAGE, null)
            preferences.storeValue(SharedPreferenceKey.PLAYER_GENDER, null)
            preferences.storeValue(SharedPreferenceKey.PLAYER_NAME, null)
        }
    }
