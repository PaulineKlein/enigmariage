package com.pklein.mariage.presentation.destinations.italie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.ui.atome.HelpCard
import com.pklein.mariage.ui.atome.ParcheminCard
import com.pklein.mariage.ui.screen.QuestionScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "jus de citron"

@AndroidEntryPoint
class SalleDinerItalieSixFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerItalie6
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
                var isClueDisplayed by rememberSaveable { mutableStateOf(false) }
                QuestionScreen(
                    title = R.string.diner_titre_italie,
                    message = annotatedStringResource(id = R.string.diner_italie_six_message),
                    enigmeContent = {
                        Column {
                            ParcheminCard(text = stringResource(id = R.string.diner_italie_six_code))
                            Spacer(modifier = Modifier.height(16.dp))
                            HelpCard(text = annotatedStringResource(id = R.string.diner_italie_six_help))
                            if (isClueDisplayed) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Image(
                                    painter = painterResource(R.drawable.image_roue_cesar),
                                    contentDescription = stringResource(id = R.string.image_indice_accessibility),
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    },
                    onHomeClick = {
                        startActivity(
                            Intent(
                                requireContext(),
                                CarnetBordUniversActivity::class.java,
                            ),
                        )
                    },
                    onClueClick = {
                        isClueDisplayed = true
                        onClueClicked()
                    },
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onClueClicked() {
        context?.let {
            Alerts.showClue(it, getString(R.string.diner_italie_six_indice)) {
                updateClue()
            }
        }
    }

    private fun updateClue() {
        gameProgress.updateClue()
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_1)) {
            gameProgress.saveTrophy(Trophies.TROPHY_CLUE_1)
            context?.let {
                Alerts.showTrophyUnlocked(
                    it,
                    getString(Trophies.TROPHY_CLUE_1.message),
                )
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_italie_six_to_seven)
    }
}
