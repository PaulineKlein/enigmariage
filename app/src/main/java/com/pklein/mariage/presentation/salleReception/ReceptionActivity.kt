package com.pklein.mariage.presentation.salleReception

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceptionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reception)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_reception) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.receptionOneFragment, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.Reception2.key -> {
                    navController.navigate(R.id.receptionTwoFragment, null, popOne)
                }

                GameStep.Reception3.key -> {
                    navController.navigate(R.id.receptionThreeFragment, null, popOne)
                }

                GameStep.Reception4.key -> {
                    navController.navigate(R.id.receptionFourFragment, null, popOne)
                }

                GameStep.Reception5.key -> {
                    navController.navigate(R.id.receptionFiveFragment, null, popOne)
                }

                GameStep.Reception6.key -> {
                    navController.navigate(R.id.receptionSixFragment, null, popOne)
                }

                GameStep.Reception7.key -> {
                    navController.navigate(R.id.CuisineOneFragment, null, popOne)
                }

                GameStep.Reception8.key -> {
                    navController.navigate(R.id.CuisineTwoFragment, null, popOne)
                }
            }
        }
    }
}
