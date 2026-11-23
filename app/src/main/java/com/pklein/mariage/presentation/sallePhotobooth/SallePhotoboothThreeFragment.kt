package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity
import com.pklein.mariage.ui.screen.CadenaPage
import com.pklein.mariage.ui.screen.ImageEnigmePage
import com.pklein.mariage.ui.screen.TwoPageScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.ui.theme.gameStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SallePhotoboothThreeFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SallePhotobooth3
    private val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        if (description == null || enigme == null) {
            throw Exception("description or enigme should not be null")
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TwoPageScreen(
                    firstPage = {
                        ImageEnigmePage(
                            title = R.string.photobooth_one_titre,
                            message = annotatedStringResource(id = R.string.photobooth_three_message_1),
                            image = enigme,
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
                            question = gameStringResource(id = description),
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
        activity?.startActivity(Intent(requireContext(), CombatFinalActivity::class.java))
        activity?.finish()
    }
}
