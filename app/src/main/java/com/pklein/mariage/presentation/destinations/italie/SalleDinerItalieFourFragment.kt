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

private const val RESPONSE = "spartacus"

@AndroidEntryPoint
class SalleDinerItalieFourFragment : Fragment() {
    private val gameStep = GameStep.SalleDinerItalie4
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
                QuestionScreen(
                    title = R.string.diner_titre_italie,
                    message = annotatedStringResource(id = R.string.diner_italie_four_message),
                    enigmeImage = R.drawable.image_semaphore_enigme,
                    enigmeImageDescription = stringResource(R.string.image_enigme_accessibility),
                    enigmeImageZoomable = true,
                    enigmeContent = {
                        Column {
                            ParcheminCard(text = stringResource(id = R.string.diner_italie_four_help))
                            Spacer(modifier = Modifier.height(16.dp))
                            Image(
                                painter = painterResource(R.drawable.image_semaphore),
                                contentDescription = stringResource(R.string.image_enigme_accessibility),
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(),
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
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) { gameProgress.updateError() }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_italie_four_to_five)
    }
}
