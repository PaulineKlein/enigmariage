package com.pklein.mariage.presentation.combatFinal.resultat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.FragmentResultsBinding
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashActivity
import com.pklein.mariage.presentation.carnetBord.CarnetBordActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity
import com.pklein.mariage.presentation.combatFinal.CombatFinalActivity.Companion.EXTRA_USELESS_TROPHY
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {
    private var binding: FragmentResultsBinding? = null
    private val resultsViewModel: ResultsViewModel by viewModels()
    private val gameProgress: GameProgressViewModel by activityViewModels()

    private val gameStep = GameStep.Result
    private val universStep = SharedPreferenceKey.UNIVERS_3
    private val trophyHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentResultsBinding
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
        gameProgress.finishUnivers(universStep)
        (requireActivity() as CombatFinalActivity).stopCountDown()

        val totalTrophiesCount = Trophies.entries.size
        val score = resultsViewModel.calculateScore()

        val newTrophies =
            buildList {
                // Clue trophies :
                if (
                    resultsViewModel.actualNbOfClue >= 6 &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_ALL)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_CLUE_ALL)
                    add(Trophies.TROPHY_CLUE_ALL)
                } else if (
                    resultsViewModel.actualNbOfClue == 0 &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_CLUE_NONE)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_CLUE_NONE)
                    add(Trophies.TROPHY_CLUE_NONE)
                }

                // Score Trophies :
                if (
                    score == 100 &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_SCORE_100)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_SCORE_100)
                    add(Trophies.TROPHY_SCORE_100)
                } else if (
                    score >= 80 &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_SCORE_80)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_SCORE_80)
                    add(Trophies.TROPHY_SCORE_80)
                }

                // Fast Score Trophy
                if (
                    resultsViewModel.isUnderThirtyMinutes() &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_SCORE_FAST)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_SCORE_FAST)
                    add(Trophies.TROPHY_SCORE_FAST)
                }

                // Useless Trophy
                if (
                    arguments?.getBoolean(EXTRA_USELESS_TROPHY) == true &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_USELESS)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_USELESS)
                    add(Trophies.TROPHY_USELESS)
                }

                // Platine Trophy
                if (
                    gameProgress.unlockedTrophiesCount == totalTrophiesCount - 1 &&
                    !gameProgress.isTrophyUnlocked(Trophies.TROPHY_PLATINE)
                ) {
                    gameProgress.saveTrophy(Trophies.TROPHY_PLATINE)
                    add(Trophies.TROPHY_PLATINE)
                }
            }
        showTrophiesSequentially(newTrophies)
        val unlockedTrophiesCount = gameProgress.unlockedTrophiesCount

        binding.tvResultatTempsReponse.text =
            resultsViewModel.calculateTime() ?: getString(R.string.erreurs)
        binding.tvResultatScoreReponse.text = getString(R.string.resultat_score_value, score)
        binding.tvResultatTropheesReponse.text =
            getString(R.string.trophy_total_count, unlockedTrophiesCount, totalTrophiesCount)

        binding.buttonResultat.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
        binding.ivHome.setOnClickListener {
            activity?.startActivity(
                Intent(
                    requireContext(),
                    CarnetBordActivity::class.java,
                ),
            )
        }
        binding.buttonShare.setOnClickListener {
            context?.let { ctx ->
                val bitmap =
                    resultsViewModel.drawTextToBitmap(
                        ctx,
                        binding.tvResultatTempsReponse.text.toString(),
                        binding.tvResultatScoreReponse.text.toString(),
                        binding.tvResultatTropheesReponse.text.toString(),
                    )
                if (bitmap != null) {
                    val contentUri = resultsViewModel.getImageUriToShare(ctx, bitmap)
                    if (contentUri != null) {
                        sharePicture(contentUri)
                    } else {
                        Alerts.showInternalError(ctx)
                    }
                } else {
                    Alerts.showInternalError(ctx)
                }
            }
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    private fun showTrophiesSequentially(trophies: List<Trophies>) {
        trophies.forEachIndexed { index, trophy ->
            trophyHandler.postDelayed({
                context?.let { Alerts.showTrophyUnlocked(it, getString(trophy.message)) }
            }, index * 7000L)
        }
    }

    private fun sharePicture(contentUri: Uri) {
        val shareIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(contentUri, requireActivity().contentResolver.getType(contentUri))
                putExtra(Intent.EXTRA_STREAM, contentUri)
            }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.resultat_share_title)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trophyHandler.removeCallbacksAndMessages(null)
        binding = null
    }
}
