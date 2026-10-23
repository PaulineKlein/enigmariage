package com.pklein.mariage.presentation.destinations.tanzanie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
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

private val RESPONSES = listOf("arbre a cacao", "cacaoyer", "cacaotier")

@AndroidEntryPoint
class SalleDinerTanzanieFourFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerTanzanie4
    private val universStep = SharedPreferenceKey.UNIVERS_2_TANZANIE
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
                QuestionScreen(
                    title = R.string.diner_titre_tanzanie,
                    message = annotatedStringResource(id = R.string.diner_tanzanie_four_message),
                    enigmeImage = R.drawable.image_zanzibar,
                    enigmeContent = {
                        Column {
                            ParcheminCard(text = stringResource(id = R.string.diner_tanzanie_four_marchand))
                            Spacer(modifier = Modifier.height(16.dp))
                            HelpCard(
                                text = annotatedStringResource(id = R.string.diner_tanzanie_four_help),
                                textAlign = TextAlign.Start,
                            )
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
                    onValidateClick = ::onValidateClicked,
                )
            }
        }
    }

    private fun onValidateClicked(response: String) {
        context?.let {
            if (response.formatAnswer() in RESPONSES) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_tanzanie_four_to_five)
    }
}
