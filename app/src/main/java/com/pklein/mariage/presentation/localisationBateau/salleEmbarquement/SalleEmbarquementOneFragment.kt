package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

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
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleEmbarquementOneFragment : Fragment() {
    private val gameStep = GameStep.SalleEmbarquement1
    private val universStep = SharedPreferenceKey.UNIVERS_1
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)

        if (description == null || enigme == null || enigmeAccessibility == null) {
            throw Exception("Informations cannot be null")
        }

        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        return ComposeView(requireContext()).apply {
            setContent {
                TwoPageScreen(
                    firstPage = {
                        ImageEnigmePage(
                            title = R.string.embarquement_one_titre,
                            message = annotatedStringResource(id = description),
                            image = enigme,
                            imageDescription = stringResource(enigmeAccessibility),
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
                            question = stringResource(id = R.string.embarquement_one_message_3),
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
        context?.let { ctx ->
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
                Alerts.showSuccess(
                    ctx,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                    PopinType.UNLOCK_SUCCESS,
                )
            } else {
                Alerts.showError(ctx) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_one_to_two)
    }
}
