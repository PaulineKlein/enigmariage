package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.introduction.screen.AddPlayerScreen
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlayerActivity : BaseActivity() {
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameProgress.storePage(GameStep.AddPlayer)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    AddPlayerScreen(onContinueClick = ::onContinueClicked)
                }
            },
        )
    }

    private fun onContinueClicked(
        name: String,
        gender: PlayerGender,
    ) {
        gameProgress.storePlayerGender(gender)
        gameProgress.storePlayerName(name)
        showPopup(name, gender)
    }

    // gender is passed in rather than read back from gameProgress: the ViewModel's
    // IInspector/gender is resolved once at ViewModel-creation time (before the player
    // picked a gender), so it would still reflect the default here, not the choice
    // that was just persisted above.
    private fun showPopup(
        name: String,
        gender: PlayerGender,
    ) {
        Alerts.showPopup(
            this,
            getString(R.string.add_player_popup, name),
            ::launchNext,
            PopinType.CLAPPING,
        )
        val trophy =
            when (gender) {
                PlayerGender.DOG -> Trophies.TROPHY_DOG
                PlayerGender.RABBIT -> Trophies.TROPHY_RABBIT
            }
        if (!gameProgress.isTrophyUnlocked(trophy)) {
            gameProgress.saveTrophy(trophy)
            Alerts.showTrophyUnlocked(this, trophy)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, CarnetBordActivity::class.java))
    }
}
