package com.pklein.mariage.data

import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored

enum class PLAYER_GENDER {
    MALE,
    FEMALE,
}

object PlayerViewModel {

    fun storeName(name: String) {
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_NAME, name)
    }

    fun storeGender(gender: PLAYER_GENDER) {
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_GENDER, gender.name)
    }

    fun storePage(page: LAST_ACTIVITY_LAUNCH) {
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_PAGE, page.name)
    }

    fun resetStorage() {
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_PAGE, null)
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_GENDER, null)
        SharedPreferenceStored.storeValue(SHARED_PREFERENCE_KEY.PLAYER_NAME, null)
    }

    fun getName(): String? {
        return SharedPreferenceStored.getValue(
            SHARED_PREFERENCE_KEY.PLAYER_NAME
        )
    }

    fun getGender(): String? {
        return SharedPreferenceStored.getValue(
            SHARED_PREFERENCE_KEY.PLAYER_GENDER
        )
    }

    fun getPage(): String? {
        return SharedPreferenceStored.getValue(
            SHARED_PREFERENCE_KEY.PLAYER_PAGE
        )
    }
}