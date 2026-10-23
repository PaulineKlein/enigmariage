package com.pklein.mariage.presentation.destinations.japon

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerJaponActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_japon)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_diner_japon) as NavHostFragment).navController

            val popOne = NavOptions.Builder()
                .setPopUpTo(R.id.salleDinerJaponOneFragment, true)
                .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerJapon2.key ->
                    navController.navigate(R.id.salleDinerJaponTwoFragment, null, popOne)

                GameStep.SalleDinerJapon3.key ->
                    navController.navigate(R.id.salleDinerJaponThreeFragment, null, popOne)

                GameStep.SalleDinerJapon4.key ->
                    navController.navigate(R.id.salleDinerJaponFourFragment, null, popOne)

                GameStep.SalleDinerJapon5.key ->
                    navController.navigate(R.id.salleDinerJaponFiveFragment, null, popOne)

                GameStep.SalleDinerJapon6.key ->
                    navController.navigate(R.id.salleDinerJaponSixFragment, null, popOne)
            }
        }
    }
}
