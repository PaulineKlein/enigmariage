package com.pklein.mariage.presentation.destinations.japon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSalleDinerJaponSixBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordUniversActivity
import com.pklein.mariage.utils.ViewPager2DotsUtils
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE_1 = "rendez vous au cambodge"
private const val RESPONSE_2 = "rendez-vous au cambodge"

@AndroidEntryPoint
class SalleDinerJaponSixFragment :
    Fragment(),
    SalleDinerJaponSixLayoutListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private var binding: FragmentSalleDinerJaponSixBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleDinerJaponSixBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        gameProgress.storePage(GameStep.SalleDinerJapon6)
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_RABBIT_TAPIOCA)) {
            gameProgress.saveTrophy(Trophies.TROPHY_RABBIT_TAPIOCA)
            context?.let {
                Alerts.showTrophyUnlocked(it, getString(Trophies.TROPHY_RABBIT_TAPIOCA.message))
            }
        }
        gameProgress.storeUniversPage(
            SharedPreferenceKey.UNIVERS_2_JAPON,
            GameStep.SalleDinerJapon6,
        )

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordUniversActivity::class.java,
                ),
            )
        }

        val adapter = SalleDinerJaponSixViewPagerAdapter(this)
        context?.let {
            val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(it)
            dotsUtils.addDotsTo(binding.pagerDots)
        }

        binding.viewpagerSalleJaponSix.adapter = adapter
        binding.viewpagerSalleJaponSix.setPageTransformer(ZoomOutPageTransformer())
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onClickButton(response: String) {
        context?.let {
            if (response.formatAnswer() == RESPONSE_1 || response.formatAnswer() == RESPONSE_2) {
                Alerts.showPopup(
                    it,
                    getString(R.string.diner_japon_six_bravo),
                    ::launchNext,
                    PopinType.CLAPPING,
                )
            } else {
                Alerts.showError(it) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        gameProgress.finishUnivers(SharedPreferenceKey.UNIVERS_2_JAPON)
        activity?.startActivity(Intent(requireContext(), CarnetBordUniversActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
