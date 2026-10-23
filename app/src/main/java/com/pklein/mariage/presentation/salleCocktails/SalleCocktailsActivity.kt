package com.pklein.mariage.presentation.salleCocktails

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleCocktailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_cocktails)
        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_cocktails) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.salleCocktailsOneFragment, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleCocktail2.key -> {
                    navController.navigate(R.id.salleCocktailsTwoFragment, null, popOne)
                }

                GameStep.SalleCocktail3.key -> {
                    navController.navigate(R.id.salleCocktailsThreeFragment, null, popOne)
                }

                GameStep.SalleCocktail4.key -> {
                    navController.navigate(R.id.salleCocktailsFourFragment, null, popOne)
                }

                GameStep.SalleCocktail5.key -> {
                    navController.navigate(R.id.salleCocktailsFiveFragment, null, popOne)
                }

                GameStep.SalleCocktail6.key -> {
                    navController.navigate(R.id.salleCocktailsSixFragment, null, popOne)
                }

                GameStep.SalleDiner1.key -> {
                    navController.navigate(R.id.salleDinerOneFragment, null, popOne)
                }

                GameStep.SalleDiner2.key -> {
                    navController.navigate(R.id.salleDinerTwoFragment, null, popOne)
                }
            }
        }
    }
}
