package com.pklein.mariage.presentation.destinations.canada

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCanadaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_canada)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_diner_canada) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.salleDinerCanadaOneFragment, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerCanada2.key -> {
                    navController.navigate(R.id.salleDinerCanadaTwoFragment, null, popOne)
                }

                GameStep.SalleDinerCanada3.key -> {
                    navController.navigate(R.id.salleDinerCanadaThreeFragment, null, popOne)
                }

                GameStep.SalleDinerCanada4.key -> {
                    navController.navigate(R.id.salleDinerCanadaFourFragment, null, popOne)
                }

                GameStep.SalleDinerCanada5.key -> {
                    navController.navigate(R.id.salleDinerCanadaFiveFragment, null, popOne)
                }

                GameStep.SalleDinerCanada6.key -> {
                    navController.navigate(R.id.salleDinerCanadaSixFragment, null, popOne)
                }
            }
        }
    }
}
