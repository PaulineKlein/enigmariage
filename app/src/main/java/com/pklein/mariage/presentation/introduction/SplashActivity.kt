package com.pklein.mariage.presentation.introduction

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.introduction.screen.SplashScreen
import com.pklein.mariage.presentation.introduction.screen.TrophyProgress
import com.pklein.mariage.presentation.trophies.TrophyActivity
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val gameProgress: GameProgressViewModel by viewModels()
    private var hasSavedGame by mutableStateOf(false)
    private var trophyProgress by mutableStateOf(
        TrophyProgress(
            unlocked = 0,
            total = Trophies.entries.size,
        ),
    )

    private val requestNotificationPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { /* permission granted or denied — nothing to do */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshState()
        setContentView(
            ComposeView(this).apply {
                setContent {
                    SplashScreen(
                        hasSavedGame = hasSavedGame,
                        trophyProgress = trophyProgress,
                        onNewGame = ::onNewGameClicked,
                        onContinue = ::onContinueClicked,
                        onTrophies = ::onTrophiesClicked,
                    )
                }
            },
        )
        requestNotificationPermissionIfNeeded()
    }

    // singleTask reuses this instance instead of recreating it, so refresh on every return to show fresh statuses.
    override fun onResume() {
        super.onResume()
        refreshState()
    }

    private fun refreshState() {
        hasSavedGame = !gameProgress.page.isNullOrEmpty()
        trophyProgress = trophyProgress.copy(unlocked = gameProgress.unlockedTrophiesCount)
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun onNewGameClicked() {
        if (hasSavedGame) {
            showAlert()
        } else {
            startActivity(Intent(this, IntroductionActivity::class.java))
        }
    }

    private fun onContinueClicked() {
        gameProgress.page?.let {
            SplashCoordinator.launchGame(it, this)
            launchCountDown()
        }
    }

    private fun onTrophiesClicked() {
        startActivity(Intent(this, TrophyActivity::class.java))
    }

    private fun showAlert() {
        Alerts.showAlert(
            this,
            getString(R.string.attention),
            getString(R.string.popup_new_game),
            getString(R.string.oui),
            getString(R.string.non),
            ::launchNewGame,
        )
    }

    private fun launchNewGame() {
        gameProgress.resetStorage()
        startActivity(Intent(this, IntroductionActivity::class.java))
    }
}
