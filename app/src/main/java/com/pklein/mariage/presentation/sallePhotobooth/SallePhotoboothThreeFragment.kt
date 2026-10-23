package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentSallePhotoboothThreeBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity
import com.pklein.mariage.utils.ViewPager2DotsUtils
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

private const val RESPONSE = "50"

@AndroidEntryPoint
class SallePhotoboothThreeFragment :
    Fragment(),
    CadenaLayoutListener,
    SallePhotoboothThreeListener {
    private val gameProgress: GameProgressViewModel by activityViewModels()
    private val gameStep = GameStep.SallePhotobooth3
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private var binding: FragmentSallePhotoboothThreeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentSallePhotoboothThreeBinding
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
        val description = gameProgress.getDescription(gameStep)
        val enigme = gameProgress.getEnigme(gameStep)
        if (description == null || enigme == null) {
            throw Exception("description or enigme should not be null")
        }

        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }

        val adapter =
            SallePhotoboothThreeAdapter(
                description = description,
                enigmeImg = enigme,
                listener = this,
                cadenaListener = this,
            )
        context?.let {
            val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(it)
            dotsUtils.addDotsTo(binding.pagerDots)
        }

        binding.viewpagerSallePhotoboothThree.adapter = adapter
        binding.viewpagerSallePhotoboothThree.setPageTransformer(ZoomOutPageTransformer())
        binding.root.addSystemWindowInsetToMargin()
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
            val responseStep = gameProgress.getReponse(gameStep)?.let { getString(it) }
            if (responseStep != null && response.formatAnswer() == responseStep) {
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
        activity?.startActivity(Intent(requireContext(), CombatFinalActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
