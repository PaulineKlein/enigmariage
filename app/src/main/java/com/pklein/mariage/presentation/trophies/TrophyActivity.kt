package com.pklein.mariage.presentation.trophies

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.trophies.screen.TrophyScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrophyActivity : BaseActivity() {
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Platine Trophy
        val totalTrophiesCount = Trophies.entries.size
        if (
            gameProgress.unlockedTrophiesCount == totalTrophiesCount - 1 &&
            !gameProgress.isTrophyUnlocked(Trophies.TROPHY_PLATINE)
        ) {
            gameProgress.saveTrophy(Trophies.TROPHY_PLATINE)
        }

        val trophies = gameProgress.getAllTrophies()
        setContentView(
            ComposeView(this).apply {
                setContent {
                    TrophyScreen(
                        trophies = trophies,
                        onBackClick = { onBackPressedDispatcher.onBackPressed() },
                    )
                }
            },
        )
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }
}
