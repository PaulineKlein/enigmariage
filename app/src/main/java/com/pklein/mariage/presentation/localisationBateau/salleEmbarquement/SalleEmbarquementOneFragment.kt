package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

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
import com.pklein.mariage.databinding.FragmentSalleEmbarquementOneBinding
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

@AndroidEntryPoint
class SalleEmbarquementOneFragment :
    Fragment(),
    CadenaLayoutListener,
    SalleEmbarquementOneLayoutListener {
    private val gameStep = GameStep.SalleEmbarquement1
    private val universStep = SharedPreferenceKey.UNIVERS_1
    private var binding: FragmentSalleEmbarquementOneBinding? = null
    private val gameProgress: GameProgressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSalleEmbarquementOneBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return

        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        val enigmeAccessibility = gameProgress.getEnigmeAccessibility(gameStep)

        if (description == null || enigme == null || enigmeAccessibility == null) {
            throw Exception("Informations cannot be null")
        }

        gameProgress.storePage(gameStep)
        gameProgress.storeUniversPage(universStep, gameStep)

        val adapter =
            SalleEmbarquementOneViewPagerAdapter(
                listener = this,
                cadenaListener = this,
                description = getText(description),
                enigmeImage = enigme,
                enigmeAccessibility = getString(enigmeAccessibility),
            )
        context?.let {
            val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(it)
            dotsUtils.addDotsTo(binding.pagerDots)
        }

        binding.viewpagerSalleEmbarquementOne.adapter = adapter
        binding.viewpagerSalleEmbarquementOne.setPageTransformer(ZoomOutPageTransformer())

        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onValidateCLicked(response: String) {
        context?.let { ctx ->
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
                Alerts.showSuccess(
                    ctx,
                    gameProgress.playerName,
                    gameProgress.gender,
                    ::launchNext,
                    PopinType.UNLOCK_SUCCESS,
                )
            } else {
                Alerts.showError(ctx) {
                    gameProgress.updateError()
                }
            }
        }
    }

    private fun launchNext() {
        findNavController().navigate(R.id.action_one_to_two)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
