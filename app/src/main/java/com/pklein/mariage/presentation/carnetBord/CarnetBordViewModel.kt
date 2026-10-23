package com.pklein.mariage.presentation.carnetBord

import androidx.lifecycle.ViewModel
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.TrophyRepository
import com.pklein.mariage.data.repository.UniversRepository
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.domain.IInspector
import com.pklein.mariage.domain.Univers2Path
import com.pklein.mariage.presentation.GameStep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarnetBordViewModel
    @Inject
    constructor(
        private val universRepository: UniversRepository,
        private val playerRepository: PlayerRepository,
        private val trophyRepository: TrophyRepository,
        private val inspector: IInspector,
    ) : ViewModel() {
        val universPath: Univers2Path get() = inspector.universPath

        fun storePage(step: GameStep) {
            playerRepository.storePage(step)
        }

        fun getUniversStatus(universKey: SharedPreferenceKey): UniversStatus = universRepository.getUniversStatus(universKey)

        fun getUniversPage(key: SharedPreferenceKey): String? = universRepository.getUniversPage(key)

        fun getUnivers2Page(): String = universRepository.getUnivers2Page(inspector)

        fun getUnivers2FinalStatus(): UniversStatus = universRepository.getUnivers2FinalStatus(inspector)

        fun isTrophyUnlocked(trophy: Trophies): Boolean = trophyRepository.isTrophyUnlocked(trophy)

        fun saveTrophy(trophy: Trophies) = trophyRepository.saveTrophy(trophy)
    }
