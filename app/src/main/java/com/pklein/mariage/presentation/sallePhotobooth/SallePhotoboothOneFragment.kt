package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.databinding.FragmentSallePhotoboothOneBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SallePhotoboothOneFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SallePhotobooth1
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private var binding: FragmentSallePhotoboothOneBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSallePhotoboothOneBinding
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
        gameProgress.storeUniversPage(universStep, gameStep)

        // setup Enigma :
        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)

        enigme?.let { binding.ivGourde.setImageResource(it) }
        enigmeAccessibility?.let {
            binding.ivGourde.contentDescription = getString(it)
        }

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.buttonPhotoboothOne.setOnClickListener {
            findNavController().navigate(R.id.action_photobooth_one_to_two)
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
