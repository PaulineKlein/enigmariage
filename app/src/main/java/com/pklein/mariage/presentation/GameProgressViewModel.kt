package com.pklein.mariage.presentation

import androidx.lifecycle.ViewModel
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.ScoreRepository
import com.pklein.mariage.data.repository.TrophyRepository
import com.pklein.mariage.data.repository.UniversRepository
import com.pklein.mariage.domain.IInspector
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameProgressViewModel
    @Inject
    constructor(
        private val playerRepository: PlayerRepository,
        private val universRepository: UniversRepository,
        private val scoreRepository: ScoreRepository,
        private val trophyRepository: TrophyRepository,
        private val inspector: IInspector,
    ) : ViewModel() {
        val playerName: String?
            get() = playerRepository.name

        val gender
            get() = inspector.gender

        val page: String?
            get() = playerRepository.page

        val unlockedTrophiesCount: Int
            get() = getAllTrophies().count { it.second }

        // Guards against re-awarding a victory trophy when the combat final screen
        // is recreated within the same playthrough (e.g. back then forward navigation).
        var victoryTrophyAwardedThisRun: Boolean = false

        fun storePage(step: GameStep) = playerRepository.storePage(step)

        fun storeUniversPage(
            key: SharedPreferenceKey,
            step: GameStep,
        ) = universRepository.storeUniversPage(key, step)

        fun finishUnivers(univers: SharedPreferenceKey) = universRepository.finishUnivers(univers)

        fun storePlayerName(name: String) = playerRepository.storeName(name)

        fun storePlayerGender(gender: PlayerGender) = playerRepository.storeGender(gender)

        fun storeStartTime() = scoreRepository.storeStartTime()

        fun storeEndTime() = scoreRepository.storeEndTime()

        fun resetStorage() = playerRepository.resetStorage()

        fun updateError() = scoreRepository.updateError()

        fun updateClue() = scoreRepository.updateClue()

        fun saveTrophy(trophy: Trophies) = trophyRepository.saveTrophy(trophy)

        fun isTrophyUnlocked(trophy: Trophies) = trophyRepository.isTrophyUnlocked(trophy)

        fun getAllTrophies() = trophyRepository.getAllTrophies()

        fun getDescription(step: GameStep?): Int? = inspector.getDescription(step)

        fun getEnigme(step: GameStep?): Int? = inspector.getEnigme(step)

        fun getEnigmeAccessibility(step: GameStep?): Int? = inspector.getEnigmeAccessibility(step)

        fun getClue(step: GameStep?): Int? = inspector.getClue(step)

        fun getReponse(step: GameStep?): Int? = inspector.getReponse(step)
    }
