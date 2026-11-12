package com.pklein.mariage.presentation.destinations.tanzanie

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerTanzanieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_tanzanie)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_tanzanie) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.salleDinerTanzanieOne, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerTanzanie2.key -> {
                    navController.navigate(R.id.salleDinerTanzanieTwo, null, popOne)
                }

                GameStep.SalleDinerTanzanie3.key -> {
                    navController.navigate(R.id.salleDinerTanzanieThree, null, popOne)
                }

                GameStep.SalleDinerTanzanie4.key -> {
                    navController.navigate(R.id.salleDinerTanzanieFour, null, popOne)
                }

                GameStep.SalleDinerTanzanie5.key -> {
                    navController.navigate(R.id.salleDinerTanzanieFive, null, popOne)
                }

                GameStep.SalleDinerTanzanie6.key -> {
                    navController.navigate(R.id.salleDinerTanzanieSix, null, popOne)
                }

                GameStep.SalleDinerTanzanie7.key -> {
                    navController.navigate(R.id.salleDinerTanzanieSeven, null, popOne)
                }
            }
        }
    }
}
