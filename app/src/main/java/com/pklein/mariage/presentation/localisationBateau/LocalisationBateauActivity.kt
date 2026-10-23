package com.pklein.mariage.presentation.localisationBateau

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalisationBateauActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localisation_bateau)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_localisation_bateau) as NavHostFragment).navController

            val popOne = NavOptions.Builder()
                .setPopUpTo(R.id.localisationBateauOneFragment, true)
                .build()

            if (intent.getBooleanExtra(EXTRA_NAVIGATE_TO_EMBARQUEMENT_THREE, false)) {
                navController.navigate(R.id.salleEmbarquementThreeFragment, null, popOne)
            } else {
                when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                    GameStep.LocalisationBateau2.key ->
                        navController.navigate(R.id.localisationBateauTwoFragment, null, popOne)

                    GameStep.SalleEmbarquement1.key ->
                        navController.navigate(R.id.salleEmbarquementOneFragment, null, popOne)

                    GameStep.SalleEmbarquement2.key ->
                        navController.navigate(R.id.salleEmbarquementTwoFragment, null, popOne)

                    GameStep.SalleEmbarquement3.key ->
                        navController.navigate(R.id.salleEmbarquementThreeFragment, null, popOne)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra(EXTRA_NAVIGATE_TO_EMBARQUEMENT_THREE, false)) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_localisation_bateau) as NavHostFragment).navController
            val popOne = NavOptions.Builder()
                .setPopUpTo(R.id.localisationBateauOneFragment, true)
                .build()
            navController.navigate(R.id.salleEmbarquementThreeFragment, null, popOne)
        }
    }

    companion object {
        const val EXTRA_NAVIGATE_TO_EMBARQUEMENT_THREE = "extra_navigate_to_embarquement_three"
    }
}
