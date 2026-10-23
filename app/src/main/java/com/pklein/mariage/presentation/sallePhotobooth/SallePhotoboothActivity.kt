package com.pklein.mariage.presentation.sallePhotobooth

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SallePhotoboothActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_photobooth)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_photobooth) as NavHostFragment).navController

            val popZero = NavOptions.Builder()
                .setPopUpTo(R.id.sallePhotoboothOneFragment, true)
                .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.SallePhotobooth2.key ->
                    navController.navigate(R.id.sallePhotoboothTwoFragment, null, popZero)

                GameStep.SallePhotobooth3.key ->
                    navController.navigate(R.id.sallePhotoboothThreeFragment, null, popZero)
            }
        }
    }
}
