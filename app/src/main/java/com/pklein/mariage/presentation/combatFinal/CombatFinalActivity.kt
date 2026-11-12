package com.pklein.mariage.presentation.combatFinal

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
class CombatFinalActivity : BaseActivity() {
    companion object {
        const val EXTRA_USELESS_TROPHY = "extra_useless_trophy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat_final)

        if (savedInstanceState == null) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_combat_final) as NavHostFragment).navController

            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.combatFinalOneFragment, true)
                    .build()

            when (intent.getStringExtra(SplashCoordinator.EXTRA_STEP)) {
                GameStep.CombatFinal2Rabbit.key -> {
                    navController.navigate(R.id.combatFinalTwoRabbitFragment, null, popOne)
                }

                GameStep.CombatFinal2Dog.key -> {
                    navController.navigate(R.id.combatFinalTwoDogFragment, null, popOne)
                }

                GameStep.CombatFinal3.key -> {
                    navController.navigate(R.id.combatFinalThreeFragment, null, popOne)
                }

                GameStep.Result.key -> {
                    navController.navigate(R.id.resultsFragment, resultsArgs(intent), popOne)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.getStringExtra(SplashCoordinator.EXTRA_STEP) == GameStep.Result.key) {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_combat_final) as? NavHostFragment)
                    ?.navController ?: return
            val popOne =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.combatFinalOneFragment, true)
                    .build()
            navController.navigate(R.id.resultsFragment, resultsArgs(intent), popOne)
        }
    }

    private fun resultsArgs(intent: Intent): Bundle? {
        if (!intent.getBooleanExtra(EXTRA_USELESS_TROPHY, false)) return null
        return Bundle().apply { putBoolean(EXTRA_USELESS_TROPHY, true) }
    }
}
