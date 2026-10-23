package com.pklein.mariage.presentation.destinations.caraibes

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCaraibesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_caraibes)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_caraibes) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.salleDinerCaraibesOne, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerCaraibes2.key -> {
                    navController.navigate(R.id.salleDinerCaraibesTwo, null, popOne)
                }

                GameStep.SalleDinerCaraibes3.key -> {
                    navController.navigate(R.id.salleDinerCaraibesThree, null, popOne)
                }

                GameStep.SalleDinerCaraibes4.key -> {
                    navController.navigate(R.id.salleDinerCaraibesFour, null, popOne)
                }

                GameStep.SalleDinerCaraibes5.key -> {
                    navController.navigate(R.id.salleDinerCaraibesFive, null, popOne)
                }

                GameStep.SalleDinerCaraibes6.key -> {
                    navController.navigate(R.id.salleDinerCaraibesSix, null, popOne)
                }
            }
        }
    }
}
