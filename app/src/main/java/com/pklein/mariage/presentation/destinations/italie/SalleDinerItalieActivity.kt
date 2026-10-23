package com.pklein.mariage.presentation.destinations.italie

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerItalieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_italie)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_italie) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.salleDinerItalieOne, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerItalie2.key -> {
                    navController.navigate(R.id.salleDinerItalieTwo, null, popOne)
                }

                GameStep.SalleDinerItalie3.key -> {
                    navController.navigate(R.id.salleDinerItalieThree, null, popOne)
                }

                GameStep.SalleDinerItalie4.key -> {
                    navController.navigate(R.id.salleDinerItalieFour, null, popOne)
                }

                GameStep.SalleDinerItalie5.key -> {
                    navController.navigate(R.id.salleDinerItalieFive, null, popOne)
                }

                GameStep.SalleDinerItalie6.key -> {
                    navController.navigate(R.id.salleDinerItalieSix, null, popOne)
                }

                GameStep.SalleDinerItalie7.key -> {
                    navController.navigate(R.id.salleDinerItalieSeven, null, popOne)
                }
            }
        }
    }
}
