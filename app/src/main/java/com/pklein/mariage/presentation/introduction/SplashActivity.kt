package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.pklein.mariage.R
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.introduction.screen.SplashScreen
import com.pklein.mariage.presentation.trophies.TrophyActivity
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    val state by viewModel.uiState.collectAsState()
                    SplashScreen(
                        hasSavedGame = state.hasSavedGame,
                        roomCode = state.roomCode,
                        trophyProgress = state.trophyProgress,
                        onNewGame = viewModel::onNewGame,
                        onContinue = ::onContinueClicked,
                        onTrophies = ::onTrophiesClicked,
                        onCreateRoom = viewModel::createRoom,
                        onJoinRoom = viewModel::joinRoom,
                        onMultiplayer = viewModel::onMultiplayer,
                        onLeaveRoom = viewModel::onLeaveRoom,
                    )
                }
            },
        )
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                state.confirmation?.let(::showConfirmation)
                if (state.launchIntro) {
                    viewModel.onIntroLaunched()
                    startActivity(Intent(this@SplashActivity, IntroductionActivity::class.java))
                }
            }
        }
    }

    // singleTask reuses this instance instead of recreating it, so refresh on every return to show fresh statuses.
    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun onContinueClicked() {
        viewModel.page?.let {
            SplashCoordinator.launchGame(it, this)
            launchCountDown()
        }
    }

    private fun onTrophiesClicked() {
        startActivity(Intent(this, TrophyActivity::class.java))
    }

    private fun showConfirmation(confirmation: SplashConfirmation) {
        val message =
            when (confirmation) {
                is SplashConfirmation.ReplaceGame -> {
                    getString(R.string.popup_new_game)
                }

                is SplashConfirmation.JoinRoom -> {
                    getString(
                        R.string.popup_join_room,
                        confirmation.roomCode,
                    )
                }

                is SplashConfirmation.SwitchRoom -> {
                    getString(
                        R.string.popup_switch_room,
                        confirmation.from,
                        confirmation.to,
                    )
                }

                is SplashConfirmation.LeaveRoom -> {
                    getString(
                        R.string.popup_leave_room,
                        confirmation.roomCode,
                    )
                }
            }
        Alerts.showAlert(
            this,
            getString(R.string.attention),
            message,
            getString(R.string.oui),
            getString(R.string.non),
            onClickButton = viewModel::confirm,
            onCancel = viewModel::dismiss,
        )
    }
}
