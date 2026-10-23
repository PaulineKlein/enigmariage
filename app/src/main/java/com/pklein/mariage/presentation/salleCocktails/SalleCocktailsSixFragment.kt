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
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSalleCocktailsSixBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.ViewPager2DotsUtils
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "nappe"

@AndroidEntryPoint
class SalleCocktailsSixFragment :
    Fragment(),
    SalleCocktailsSevenLayoutListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleCocktailsSixBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleCocktailsSixBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleCocktail6)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_CACTUS)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_CACTUS)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_RABBIT_CACTUS.message))
            }
        }
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_COCKTAILS,
            GameStep.SalleCocktail6,
        )

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        val adapter = SalleCocktailsSevenViewPagerAdapter(this)
        context?.let {
            val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(it)
            dotsUtils.addDotsTo(binding.pagerDots)
        }

        binding.viewpagerSalleCocktailsSeven.adapter = adapter
        binding.viewpagerSalleCocktailsSeven.setPageTransformer(ZoomOutPageTransformer())
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onClickButton(response: String) {
        context?.let {
            if (response.formatAnswer().contains(RESPONSE)) {
                Alerts.showSuccess(it, gameProgress.playerName, gameProgress.gender, ::launchNext)
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_six_to_salle_diner_one)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
