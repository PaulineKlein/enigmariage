package com.pklein.mariage.presentation.carnetBord

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.carnetBord.screen.CarnetBordUniversScreen
import com.pklein.mariage.presentation.carnetBord.screen.DestinationState
import com.pklein.mariage.presentation.carnetBord.screen.UniversCardState
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarnetBordUniversActivity : BaseActivity() {
    private val viewModel: CarnetBordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.storePage(GameStep.CarnetBord2)

        val allFinished = isUnivers2Finish()
        val destinations = buildDestinations()
        setContentView(
            ComposeView(this).apply {
                setContent {
                    CarnetBordUniversScreen(
                        destinations = destinations,
                        playerName = viewModel.playerName,
                        allFinished = allFinished,
                        onHomeClick = {
                            startActivity(
                                Intent(
                                    this@CarnetBordUniversActivity,
                                    CarnetBordActivity::class.java,
                                ),
                            )
                        },
                        onUniversClick = { step ->
                            SplashCoordinator.launchGame(step, this@CarnetBordUniversActivity)
                        },
                        onCheckShown = viewModel::markCheckShown,
                        onBravoClick = {
                            startActivity(
                                Intent(
                                    this@CarnetBordUniversActivity,
                                    CarnetBordActivity::class.java,
                                ),
                            )
                            finish()
                        },
                    )
                }
            },
        )

        if (allFinished) {
            val trophy = viewModel.universPath.completeTrophy
            if (!viewModel.isTrophyUnlocked(trophy)) {
                viewModel.saveTrophy(trophy)
                Alerts.showTrophyUnlocked(this, trophy)
            }
        }
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }

    private fun buildDestinations(): List<DestinationState> {
        val path = viewModel.universPath
        return path.subUnivers.indices.map { i ->
            val universKey = path.subUnivers[i]
            val status = viewModel.getUniversStatus(universKey)
            val finished = status == UniversStatus.IS_FINISHED
            var step = path.subUniversFirstStep[i].name
            if (status == UniversStatus.GOING) {
                viewModel.getUniversPage(universKey)?.let { step = it }
            }
            DestinationState(
                card =
                    UniversCardState(
                        image = null,
                        nameRes = path.subUniversName[i],
                        // Not started and going look the same: an open, blue button.
                        status = if (finished) UniversStatus.IS_FINISHED else UniversStatus.GOING,
                        clickable = !finished,
                        step = step,
                        id = universKey.name,
                        animateCheck = finished && !viewModel.isCheckShown(universKey.name),
                    ),
                mapOverlay = path.subUniversMapmonde[i],
            )
        }
    }

    private fun isUnivers2Finish(): Boolean =
        viewModel.universPath.subUnivers.all {
            viewModel.getUniversStatus(it) == UniversStatus.IS_FINISHED
        }
}
