package com.pklein.mariage.presentation.destinations.cambodge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSalleDinerCambodgeFiveBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalleDinerCambodgeFiveFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SalleDinerCambodge5
    private val universStep = SharedPreferenceKey.UNIVERS_2_CAMBODGE
    private var binding: FragmentSalleDinerCambodgeFiveBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleDinerCambodgeFiveBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(gameStep)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_MANGUE)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_MANGUE)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_RABBIT_MANGUE.message))
            }
        }
        gameProgress.storeUniversPage(universStep, gameStep)

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java,
                ),
            )
        }
        binding.buttonCambodgeFive.setOnClickListener {
            gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_CAMBODGE)
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java,
                ),
            )
            activity?.finish()
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
