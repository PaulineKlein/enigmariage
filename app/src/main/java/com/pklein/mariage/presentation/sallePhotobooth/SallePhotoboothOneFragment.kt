package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.sallePhotobooth.screen.PhotoboothOneScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SallePhotoboothOneFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SallePhotobooth1
    private val universStep = SharedPreferenceKey.UNIVERS_3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)

        if (enigme == null || enigmeAccessibility == null) {
            throw Exception("Informations cannot be null")
        }

        return ComposeView(requireContext()).apply {
            setContent {
                PhotoboothOneScreen(
                    enigme = enigme,
                    enigmeDescription = enigmeAccessibility,
                    onHomeClick = {
                        startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
                    },
                    onContinueClick = {
                        findNavController().navigate(R.id.action_photobooth_one_to_two)
                    },
                )
            }
        }
    }
}
