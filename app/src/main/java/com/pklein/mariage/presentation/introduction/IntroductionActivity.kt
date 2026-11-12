package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.introduction.screen.IntroductionScreen
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : BaseActivity() {
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            SharedPreferenceStored.resetPreference()
            gameProgress.storePage(GameStep.Introduction)
            stopCountDown()
        }
        setContentView(
            ComposeView(this).apply {
                setContent {
                    IntroductionScreen(
                        showSkip = gameProgress.isTrophyUnlocked(Trophies.TROPHY_VICTOIRE_1),
                        onNoxTapped = ::onNoxTapped,
                        onStartClick = ::onStartClicked,
                    )
                }
            },
        )
    }

    private fun onStartClicked() {
        gameProgress.storeStartTime()
        launchCountDown()
        startActivity(Intent(this, AddPlayerActivity::class.java))
    }

    private fun onNoxTapped() {
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DONT_TOUCH)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DONT_TOUCH)
            Alerts.showTrophyUnlocked(this, Trophies.TROPHY_DONT_TOUCH)
        }
    }
}
