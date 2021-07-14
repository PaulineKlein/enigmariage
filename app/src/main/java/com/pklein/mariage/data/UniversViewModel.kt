package com.pklein.mariage.data

import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored

enum class UNIVERS_STATUS {
    NOT_STARTED,
    GOING,
    IS_FINISHED
}

const val FINISH = "FINISH"

object UniversViewModel {

    fun getUniversPage(univers: SHARED_PREFERENCE_KEY): String? {
        return SharedPreferenceStored.getValue(univers)
    }

    fun getUnivers2Page(): String {
        val step = getUniversPage(SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS)

        return when (step) {
            null, "" -> LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_1.name
            FINISH -> LAST_ACTIVITY_LAUNCH.CARNET_BORD2.name
            else -> step
        }
    }

    fun getUniversStatus(univers: SHARED_PREFERENCE_KEY): UNIVERS_STATUS {
        val step = SharedPreferenceStored.getValue(univers)
        return when (step) {
            null, "" -> UNIVERS_STATUS.NOT_STARTED
            FINISH -> UNIVERS_STATUS.IS_FINISHED
            else -> UNIVERS_STATUS.GOING
        }
    }

    fun getUnivers2FinalStatus(): UNIVERS_STATUS {
        val stepCocktail = getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_2_COCKTAILS)
        val stepCanada = getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA)
        val stepCambodge = getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_2_CAMBODGE)
        val stepJapon = getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON)

        return if (stepCocktail == UNIVERS_STATUS.NOT_STARTED) {
            UNIVERS_STATUS.NOT_STARTED
        } else if (stepCocktail == UNIVERS_STATUS.IS_FINISHED &&
            stepCanada == UNIVERS_STATUS.IS_FINISHED &&
            stepCambodge == UNIVERS_STATUS.IS_FINISHED &&
            stepJapon == UNIVERS_STATUS.IS_FINISHED
        ) {
            UNIVERS_STATUS.IS_FINISHED
        } else {
            UNIVERS_STATUS.GOING
        }
    }

    fun storeUniversPage(univers: SHARED_PREFERENCE_KEY, page: LAST_ACTIVITY_LAUNCH) {
        SharedPreferenceStored.storeValue(univers, page.name)
    }

    fun finishUnivers(univers: SHARED_PREFERENCE_KEY) {
        SharedPreferenceStored.storeValue(univers, FINISH)
    }
}