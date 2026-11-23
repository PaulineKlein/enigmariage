package com.pklein.mariage.presentation.salleCocktails.salleDiner

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
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.ui.screen.CadenaPage
import com.pklein.mariage.ui.screen.ImageEnigmePage
import com.pklein.mariage.ui.screen.TwoPageScreen
import com.pklein.mariage.ui.theme.gameAnnotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "2010"

@AndroidEntryPoint
class SalleDinerOneFragment : Fragment() {
    private val gameStep = GameStep.SalleDiner1
    private val universStep = SharedPreferenceKey.UNIVERS_2_COCKTAILS
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
                            title = R.string.diner_one_titre,
                            message = gameAnnotatedStringResource(id = R.string.diner_one_message_1),
                            image = R.drawable.image_diner,
                            imageDescription = stringResource(id = R.string.image_enigme_accessibility),
                            onHomeClick = {
                                startActivity(
                                    Intent(
                                        requireContext(),
                                        CarnetBordActivity::class.java,
                                    ),
                                )
                            },
                            onImageClick = ::onImageClicked,
                        )
                    },
                    secondPage = {
                        CadenaPage(
                            question =
                                stringResource(
                                    id = R.string.diner_one_message_2,
                                    gameProgress.playerName.orEmpty(),
                                ),
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
                Alerts.showTrophyUnlocked(it, Trophies.TROPHY_LENS)
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(
                    it,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                    PopinType.UNLOCK_SUCCESS,
                )
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_diner_one_to_two)
    }
}
