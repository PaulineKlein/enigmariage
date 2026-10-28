package com.pklein.mariage.presentation.salleCocktails

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
import com.pklein.mariage.databinding.FragmentSalleCocktailsFourBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.ui.theme.getFormattedText
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

private enum class CITY {
    GERUDO,
    COCORICO,
}

@AndroidEntryPoint
class SalleCocktailsFourFragment : Fragment() {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleCocktailsFourBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleCocktailsFourBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleCocktail4)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_COCKTAILS,
            GameStep.SalleCocktail4,
        )

        binding.tvCocktailFourMessage.text =
            resources.getFormattedText(R.string.cocktail_four_message, gameProgress.playerName)

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.radioGroupMap.setOnCheckedChangeListener { _, _ ->
            binding.buttonCocktailFour.isEnabled = true
        }
        binding.ivDesert.setOnClickListener {
            binding.radioDesert.isChecked = true
            binding.radioVillage.isChecked = false
        }
        binding.ivVillage.setOnClickListener {
            binding.radioDesert.isChecked = false
            binding.radioVillage.isChecked = true
        }
        binding.buttonCocktailFour.setOnClickListener {
            onValidateClicked(checkValueFromRadioButton())
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun onValidateClicked(city: CITY) {
        context?.let {
            if (city == CITY.GERUDO) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun checkValueFromRadioButton(): CITY {
        val mapId = binding?.radioGroupMap?.checkedRadioButtonId ?: return CITY.COCORICO
        return if (resources.getResourceEntryName(mapId) == "radioDesert") {
            CITY.GERUDO
        } else {
            CITY.COCORICO
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_four_to_five)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
