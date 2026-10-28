package com.pklein.mariage.presentation.carnetBord

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.databinding.ActivityCarnetBordUniversBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.ui.theme.getFormattedText
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarnetBordUniversActivity : BaseActivity() {
    private lateinit var binding: ActivityCarnetBordUniversBinding
    private val viewModel: CarnetBordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetBordUniversBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.storePage(GameStep.CarnetBord2)

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        val path = viewModel.universPath
        path.subUnivers.indices.forEach { i ->
            val button =
                layoutInflater.inflate(
                    R.layout.item_univers_button,
                    binding.universButtonsLayout,
                    false,
                ) as AppCompatButton
            button.setText(path.subUniversName[i])
            binding.universButtonsLayout.addView(button)
            showButton(
                path.subUnivers[i],
                path.subUniversFirstStep[i],
                button,
                findViewById(path.subUniversIvMapmonde[i]),
                path.subUniversName[i],
            )
        }
        if (isUnivers2Finish()) {
            binding.tvCarnetBravo.apply {
                text = resources.getFormattedText(R.string.carnet_bord2_bravo, viewModel.playerName)
                visibility = View.VISIBLE
            }
            binding.tvCarnetBravo.setOnClickListener {
                startActivity(Intent(this, CarnetBordActivity::class.java))
                finish()
            }
            val trophy = path.completeTrophy
            if (!viewModel.isTrophyUnlocked(trophy)) {
                viewModel.saveTrophy(trophy)
                Alerts.showTrophyUnlocked(this, getString(trophy.message))
            }
        } else {
            binding.tvCarnetBravo.visibility = View.INVISIBLE
        }
        binding.root.addSystemWindowInsetToMargin()
    }

    // singleTask reuses the existing instance instead of calling onCreate, so rebuild the screen to show fresh statuses.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }

    private fun universAccessibility(
        nameRes: Int,
        stateRes: Int,
    ): String = "${getString(nameRes)}, ${getString(stateRes)}"

    private fun showButton(
        universKey: SharedPreferenceKey,
        activityLaunch: GameStep,
        universButton: AppCompatButton,
        ivMapmonde: AppCompatImageView,
        nameRes: Int,
    ) {
        val step = viewModel.getUniversStatus(universKey)
        var activityGoing = activityLaunch.name
        when (step) {
            UniversStatus.NOT_STARTED -> {
                universButton.isEnabled = true
                universButton.contentDescription =
                    universAccessibility(nameRes, R.string.univers_state_going)
            }

            UniversStatus.GOING -> {
                universButton.isEnabled = true
                universButton.contentDescription =
                    universAccessibility(nameRes, R.string.univers_state_going)
                viewModel.getUniversPage(universKey)?.let { activityGoing = it }
            }

            UniversStatus.IS_FINISHED -> {
                universButton.isEnabled = false
                universButton.isFocusable = false
                universButton.setBackgroundResource(R.drawable.background_button_finished)
                universButton.setTextColor(ContextCompat.getColor(this, R.color.help_text))
                val checkIcon = ContextCompat.getDrawable(this, R.drawable.ic_check)
                checkIcon?.setTint(ContextCompat.getColor(this, R.color.help_text))
                universButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    checkIcon,
                    null,
                )
                universButton.compoundDrawablePadding =
                    resources.getDimensionPixelSize(R.dimen.margin_8)
                universButton.contentDescription =
                    universAccessibility(nameRes, R.string.univers_state_finished)
                ivMapmonde.visibility = View.VISIBLE
            }
        }
        universButton.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun isUnivers2Finish(): Boolean =
        viewModel.universPath.subUnivers.all {
            viewModel.getUniversStatus(it) == UniversStatus.IS_FINISHED
        }
}
