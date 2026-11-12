package com.pklein.mariage.presentation.destinations.cambodge

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCambodgeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_cambodge)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_diner_cambodge) as NavHostFragment).navController

            val popOne = NavOptions.Builder()
                .setPopUpTo(R.id.salleDinerCambodgeOneFragment, true)
                .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SalleDinerCambodge2.key ->
                    navController.navigate(R.id.salleDinerCambodgeTwoFragment, null, popOne)

                GameStep.SalleDinerCambodge3.key ->
                    navController.navigate(R.id.salleDinerCambodgeThreeFragment, null, popOne)

                GameStep.SalleDinerCambodge4.key ->
                    navController.navigate(R.id.salleDinerCambodgeFourFragment, null, popOne)

                GameStep.SalleDinerCambodge5.key ->
                    navController.navigate(R.id.salleDinerCambodgeFiveFragment, null, popOne)
            }
        }
    }
}
