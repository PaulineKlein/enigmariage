package com.pklein.mariage.presentation.destinations.italie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.atome.GlassContent
import com.pklein.mariage.ui.atome.IceBlockState
import com.pklein.mariage.ui.screen.IceBlockScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerItalieSevenFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerItalie7
    private val universStep = SharedPreferenceKey.UNIVERS_2_ITALIE
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        return ComposeView(requireContext()).apply {
            setContent {
                val block = rememberSaveable(saver = IceBlockState.Saver) { IceBlockState() }
                IceBlockScreen(
                    title = R.string.diner_titre_italie,
                    message = annotatedStringResource(id = R.string.diner_italie_seven_message),
                    state = block,
                    glassContent = GlassContent.LemonJuice,
                    blockDescription = R.string.diner_italie_seven_description,
                    resultMessage = R.string.diner_italie_seven_ingredient,
                    resultAction = {
                        saveTrophy()
                    },
                    onHomeClick = {
                        startActivity(
                            Intent(
                                requireContext(),
                                CarnetBordUniversActivity::class.java,
                            ),
                        )
                    },
                    onContinueClick = ::launchNext,
                )
            }
        }
    }

    private fun saveTrophy() {
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DOG_CITRON)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DOG_CITRON)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_DOG_CITRON.message))
            }
        }
    }

    private fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_ITALIE)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
        activity?.finish()
    }
}
