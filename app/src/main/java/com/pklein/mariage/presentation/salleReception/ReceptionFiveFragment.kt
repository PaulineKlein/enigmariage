package com.pklein.mariage.presentation.salleReception

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.ui.atome.GlassContent
import com.pklein.mariage.ui.atome.IceBlockState
import com.pklein.mariage.ui.screen.IceBlockScreen
import com.pklein.mariage.ui.theme.annotatedStringResource
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceptionFiveFragment : Fragment() {
    private val gameStep = GameStep.Reception5
    private val universStep = SharedPreferenceKey.UNIVERS_2_RECEPTION
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
                val iceBlock = rememberSaveable(saver = IceBlockState.Saver) { IceBlockState() }
                IceBlockScreen(
                    title = R.string.chambre_froide_title,
                    message = annotatedStringResource(id = R.string.reception_five_message),
                    state = iceBlock,
                    glassContent = GlassContent.CrushedIce,
                    blockDescription = R.string.reception_five_ice_description,
                    resultMessage = R.string.reception_five_ingredient,
                    resultAction = {
                        saveTrophy()
                    },
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onContinueClick = ::launchNext,
                )
            }
        }
    }

    private fun saveTrophy() {
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DOG_GLACE_PILEE)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DOG_GLACE_PILEE)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_DOG_GLACE_PILEE.message))
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_reception_five_to_six)
    }
}
