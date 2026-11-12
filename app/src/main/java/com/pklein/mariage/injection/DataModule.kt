package com.pklein.mariage.injection

import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.AgentCroquette
import com.pklein.mariage.data.model.CapitaineCarotte
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.domain.IInspector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideSharedPreferenceStored(): SharedPreferenceStored = SharedPreferenceStored

    // Not @Singleton on purpose: the inspector must be re-derived from the persisted
    // gender on every injection, otherwise whoever gets injected first (possibly before
    // the player has even picked a gender) would freeze that value for the whole process.
    @Provides
    fun provideInspector(playerRepository: PlayerRepository): IInspector {
        return if (playerRepository.gender == PlayerGender.DOG.name) {
            AgentCroquette()
        } else {
            CapitaineCarotte()
        }
    }
}
