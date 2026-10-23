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
import com.pklein.mariage.databinding.FragmentSalleCocktailsOneBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.utils.ViewPager2DotsUtils
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "124"

@AndroidEntryPoint
class SalleCocktailsOneFragment :
    Fragment(),
    CadenaLayoutListener,
    SalleCocktailsOneLayoutListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleCocktailsOneBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleCocktailsOneBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleCocktail1)
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_COCKTAILS,
            GameStep.SalleCocktail1,
        )

        val adapter = SalleCocktailsOneViewPagerAdapter(this, this)
        context?.let {
            val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(it)
            dotsUtils.addDotsTo(binding.pagerDots)
        }
        binding.viewpagerSalleCocktailsOne.adapter = adapter
        binding.viewpagerSalleCocktailsOne.setPageTransformer(ZoomOutPageTransformer())
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onClickButtonHome() {
        activity?.startActivity(Intent(requireContext(), CarnetBordActivity::class.java))
    }

    override fun onClickImage(drawable: Int) {
        context?.let {
            showImageWithStfalconViewer(it, drawable)
            if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_LENS)) {
                gameProgress.saveTrophy(Trophies.TROPHY_LENS)
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_LENS.message))
            }
        }
    }

    override fun onValidateCLicked(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE) {
                Alerts.showSuccess(
                    it,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                    PopinType.UNLOCK_SUCCESS,
                )
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_one_to_two)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
