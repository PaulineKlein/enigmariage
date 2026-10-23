package com.pklein.mariage.presentation.destinations.caraibes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.screen.CadenaPage
import com.pklein.mariage.ui.screen.ImageEnigmePage
import com.pklein.mariage.ui.screen.TwoPageScreen
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "834"

@AndroidEntryPoint
class SalleDinerCaraibesFourFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerCaraibes4
    private val universStep = SharedPreferenceKey.UNIVERS_2_CARAIBES
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
                TwoPageScreen(
                    firstPage = {
                        ImageEnigmePage(
                            title = R.string.diner_titre_caraibes,
                            message = stringResource(id = R.string.diner_caraibes_four_message),
                            image = R.drawable.image_coco,
                            imageDescription = stringResource(id = R.string.image_enigme_accessibility),
                            onHomeClick = {
                                startActivity(
                                    Intent(
                                        requireContext(),
                                        CarnetBordUniversActivity::class.java,
                                    ),
                                )
                            },
                            onImageClick = ::onImageClicked,
                        )
                    },
                    secondPage = {
                        CadenaPage(
                            question = stringResource(id = R.string.diner_caraibes_four_question),
                            onValidateClick = ::onValidateClicked,
                        )
                    },
                )
            }
        }
    }

    private fun onImageClicked(drawable: Int) {
        context?.let {
            showImageWithStfalconViewer(it, drawable)
            if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_LENS)) {
                gameProgress.saveTrophy(Trophies.TROPHY_LENS)
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_LENS.message))
            }
        }
    }

    private fun onValidateClicked(answer: String) {
        context?.let {
            if (answer == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_caraibes_four_to_five)
    }
}
