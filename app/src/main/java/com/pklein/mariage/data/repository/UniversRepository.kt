package com.pklein.mariage.data.repository

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.domain.IInspector
import com.pklein.mariage.presentation.GameStep
import javax.inject.Inject

enum class UniversStatus {
    NOT_STARTED,
    GOING,
    IS_FINISHED,
}

private const val FINISH = "FINISH"

class UniversRepository
    @Inject
    constructor(
        private val preferences: SharedPreferenceStored,
    ) {
        fun getUniversPage(univers: SharedPreferenceKey): String? = preferences.getValue(univers)

        fun getUnivers2Page(inspector: IInspector): String {
            val path = inspector.universPath
            return when (val step = getUniversPage(path.mainUnivers)) {
                null, "" -> path.firstStep.name
                FINISH -> GameStep.CarnetBord2.name
                else -> step
            }
        }

        fun getUnivers2FinalStatus(inspector: IInspector): UniversStatus {
            val path = inspector.universPath
            return when (getUniversStatus(path.mainUnivers)) {
                UniversStatus.NOT_STARTED -> {
                    UniversStatus.NOT_STARTED
                }

                UniversStatus.IS_FINISHED -> {
                    if (path.subUnivers.all { getUniversStatus(it) == UniversStatus.IS_FINISHED }) {
                        UniversStatus.IS_FINISHED
                    } else {
                        UniversStatus.GOING
                    }
                }

                UniversStatus.GOING -> {
                    UniversStatus.GOING
                }
            }
        }

        fun getUniversStatus(univers: SharedPreferenceKey): UniversStatus {
            val step = preferences.getValue(univers)
            return when (step) {
                null, "" -> UniversStatus.NOT_STARTED
                FINISH -> UniversStatus.IS_FINISHED
                else -> UniversStatus.GOING
            }
        }

        fun storeUniversPage(
            univers: SharedPreferenceKey,
            page: GameStep,
        ) {
            // The destination activity stays in the back stack after finishing; if it gets recreated,
            // its fragment calls this again and must not turn a finished univers back into "going".
            if (getUniversStatus(univers) == UniversStatus.IS_FINISHED) return
            preferences.storeValue(univers, page.name)
        }

        fun finishUnivers(univers: SharedPreferenceKey) {
            preferences.storeValue(univers, FINISH)
        }
    }
