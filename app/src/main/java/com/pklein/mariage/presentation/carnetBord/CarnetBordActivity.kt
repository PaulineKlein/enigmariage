package com.pklein.mariage.presentation.carnetBord

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.carnetBord.screen.CarnetBordScreen
import com.pklein.mariage.presentation.carnetBord.screen.UniversCardState
import com.pklein.mariage.presentation.trophies.TrophyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarnetBordActivity : BaseActivity() {
    private val viewModel: CarnetBordViewModel by viewModels()
    private var universCards by mutableStateOf<List<UniversCardState>>(emptyList())
    private var unlockedTrophies by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.storePage(GameStep.CarnetBord)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    CarnetBordScreen(
                        univers = universCards,
                        unlockedTrophies = unlockedTrophies,
                        totalTrophies = viewModel.totalTrophiesCount,
                        onUniversClick = { step ->
                            SplashCoordinator.launchGame(
                                step,
                                this@CarnetBordActivity,
                            )
                        },
                        onTrophiesClick = {
                            startActivity(
                                Intent(
                                    this@CarnetBordActivity,
                                    TrophyActivity::class.java,
                                ),
                            )
                        },
                    )
                }
            },
        )
    }

    override fun onResume() {
        super.onResume()
        refreshState()
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }

    private fun refreshState() {
        val step1 = viewModel.getUniversStatus(SharedPreferenceKey.UNIVERS_1)
        val step2 = viewModel.getUnivers2FinalStatus()
        val step3 = viewModel.getUniversStatus(SharedPreferenceKey.UNIVERS_3)
        universCards =
            listOf(univers1Card(step1), univers2Card(step2, step1), univers3Card(step3, step2))
        unlockedTrophies = viewModel.unlockedTrophiesCount
    }

    private fun univers1Card(status: UniversStatus): UniversCardState {
        var step = GameStep.LocalisationBateau1.name
        if (status == UniversStatus.GOING) {
            viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_1)?.let { step = it }
        }
        return UniversCardState(
            image = R.drawable.image_carnet_bord1,
            nameRes = R.string.univers1_name,
            status = if (status == UniversStatus.IS_FINISHED) UniversStatus.IS_FINISHED else UniversStatus.GOING,
            clickable = status != UniversStatus.IS_FINISHED,
            step = step,
        )
    }

    private fun univers2Card(
        status: UniversStatus,
        previous: UniversStatus,
    ): UniversCardState {
        val displayed = displayedStatus(status, previous)
        return UniversCardState(
            image = R.drawable.image_carnet_bord2,
            nameRes = R.string.univers2_name,
            status = displayed,
            clickable = displayed != UniversStatus.NOT_STARTED,
            step = if (displayed == UniversStatus.IS_FINISHED) GameStep.CarnetBord2.name else viewModel.getUnivers2Page(),
        )
    }

    private fun univers3Card(
        status: UniversStatus,
        previous: UniversStatus,
    ): UniversCardState {
        var step = GameStep.SallePhotobooth1.name
        if (status == UniversStatus.GOING) {
            viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_3)?.let { step = it }
        }
        val displayed = displayedStatus(status, previous)
        return UniversCardState(
            image = R.drawable.image_carnet_bord3,
            nameRes = if (displayed == UniversStatus.NOT_STARTED) R.string.univers3_name_locked else R.string.univers3_name,
            status = displayed,
            clickable = displayed != UniversStatus.NOT_STARTED,
            step = if (displayed == UniversStatus.IS_FINISHED) GameStep.Result.name else step,
        )
    }

    // A universe that was never started stays locked until the previous one is finished.
    private fun displayedStatus(
        status: UniversStatus,
        previous: UniversStatus,
    ): UniversStatus =
        when {
            status == UniversStatus.NOT_STARTED && previous != UniversStatus.IS_FINISHED -> UniversStatus.NOT_STARTED
            status == UniversStatus.IS_FINISHED -> UniversStatus.IS_FINISHED
            else -> UniversStatus.GOING
        }
}
