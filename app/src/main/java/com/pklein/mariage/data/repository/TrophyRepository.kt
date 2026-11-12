package com.pklein.mariage.data.repository

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.Trophies
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrophyRepository
    @Inject
    constructor(
        private val preferences: SharedPreferenceStored,
    ) {
        fun saveTrophy(trophy: Trophies) {
            if (!isTrophyUnlocked(trophy)) {
                preferences.saveTrophy(trophy)
            }
        }

        fun isTrophyUnlocked(trophy: Trophies): Boolean = preferences.getTrophy(trophy)

        fun getAllTrophies(): List<Pair<Trophies, Boolean>> = Trophies.entries.map { it to isTrophyUnlocked(it) }
    }
