package com.pklein.mariage.data.repository

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.GameNames
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

        val roomCode: String?
            get() = preferences.getValue(SharedPreferenceKey.ROOM_CODE)

        val gameNames: GameNames
            get() =
                GameNames.of(
                    bride = preferences.getValue(SharedPreferenceKey.BRIDE_NAME),
                    groom = preferences.getValue(SharedPreferenceKey.GROOM_NAME),
                    villain = preferences.getValue(SharedPreferenceKey.VILLAIN_NAME),
                )

        fun storeGameNames(names: GameNames) {
            preferences.storeValue(SharedPreferenceKey.BRIDE_NAME, names.bride)
            preferences.storeValue(SharedPreferenceKey.GROOM_NAME, names.groom)
            preferences.storeValue(SharedPreferenceKey.VILLAIN_NAME, names.villain)
        }

        fun storeName(name: String) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_NAME, name)
        }

        fun storeGender(gender: PlayerGender) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_GENDER, gender.name)
        }

        fun storeRoomCode(roomCode: String) {
            preferences.storeValue(SharedPreferenceKey.ROOM_CODE, roomCode)
        }

        fun storePage(page: GameStep) {
            preferences.storeValue(SharedPreferenceKey.PLAYER_PAGE, page.name)
        }

        fun resetStorage() {
            preferences.storeValue(SharedPreferenceKey.PLAYER_PAGE, null)
            preferences.storeValue(SharedPreferenceKey.PLAYER_GENDER, null)
            preferences.storeValue(SharedPreferenceKey.PLAYER_NAME, null)
            preferences.storeValue(SharedPreferenceKey.ROOM_CODE, null)
            preferences.storeValue(SharedPreferenceKey.BRIDE_NAME, null)
            preferences.storeValue(SharedPreferenceKey.GROOM_NAME, null)
            preferences.storeValue(SharedPreferenceKey.VILLAIN_NAME, null)
        }
    }
