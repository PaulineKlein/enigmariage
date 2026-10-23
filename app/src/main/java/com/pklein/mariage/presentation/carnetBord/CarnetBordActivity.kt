package com.pklein.mariage.presentation.carnetBord

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.pklein.mariage.R
import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.databinding.ActivityCarnetBordBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.presentation.SplashCoordinator
import com.pklein.mariage.presentation.trophies.TrophyActivity
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.extension.defineButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarnetBordActivity : BaseActivity() {
    lateinit var binding: ActivityCarnetBordBinding
    private val viewModel: CarnetBordViewModel by viewModels()
    private var step1 = UniversStatus.NOT_STARTED
    private var step2 = UniversStatus.NOT_STARTED
    private var step3 = UniversStatus.NOT_STARTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetBordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.storePage(GameStep.CarnetBord)
        showUnivers1()
        showUnivers2()
        showUnivers3()
        binding.buttonTrophees.setOnClickListener {
            startActivity(Intent(this, TrophyActivity::class.java))
        }

        binding.root.addSystemWindowInsetToMargin()
    }

    private fun universAccessibility(
        nameRes: Int,
        stateRes: Int,
    ): String = "${getString(nameRes)}, ${getString(stateRes)}"

    private fun showUnivers1() {
        step1 = viewModel.getUniversStatus(SharedPreferenceKey.UNIVERS_1)
        var activityGoing = GameStep.LocalisationBateau1.name
        when (step1) {
            UniversStatus.NOT_STARTED -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_encours, true, this)
                binding.ivUnivers1.contentDescription =
                    universAccessibility(R.string.univers1_name, R.string.univers_state_going)
            }

            UniversStatus.GOING -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_encours, true, this)
                binding.ivUnivers1.contentDescription =
                    universAccessibility(R.string.univers1_name, R.string.univers_state_going)
                viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_1)?.let {
                    activityGoing = it
                }
            }

            UniversStatus.IS_FINISHED -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_finish, false, this)
                binding.ivUnivers1.contentDescription =
                    universAccessibility(R.string.univers1_name, R.string.univers_state_finished)
            }
        }

        binding.ivUnivers1.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun showUnivers2() {
        step2 = viewModel.getUnivers2FinalStatus()
        var activityGoing = viewModel.getUnivers2Page()
        when (step2) {
            UniversStatus.NOT_STARTED -> {
                if (step1 != UniversStatus.IS_FINISHED) {
                    binding.ivUnivers2.defineButton(R.drawable.image_univers2_wait, false, this)
                    binding.ivUnivers2.contentDescription =
                        universAccessibility(
                            R.string.univers2_name,
                            R.string.univers_state_not_started,
                        )
                } else {
                    binding.ivUnivers2.defineButton(R.drawable.image_univers2_encours, true, this)
                    binding.ivUnivers2.contentDescription =
                        universAccessibility(R.string.univers2_name, R.string.univers_state_going)
                }
            }

            UniversStatus.GOING -> {
                binding.ivUnivers2.defineButton(R.drawable.image_univers2_encours, true, this)
                binding.ivUnivers2.contentDescription =
                    universAccessibility(R.string.univers2_name, R.string.univers_state_going)
            }

            UniversStatus.IS_FINISHED -> {
                binding.ivUnivers2.defineButton(R.drawable.image_univers2_finish, true, this)
                binding.ivUnivers2.contentDescription =
                    universAccessibility(R.string.univers2_name, R.string.univers_state_finished)
                activityGoing = GameStep.CarnetBord2.name
            }
        }

        binding.ivUnivers2.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun showUnivers3() {
        step3 = viewModel.getUniversStatus(SharedPreferenceKey.UNIVERS_3)
        var activityGoing = GameStep.SallePhotobooth1.name
        when (step3) {
            UniversStatus.NOT_STARTED -> {
                if (step2 != UniversStatus.IS_FINISHED) {
                    binding.ivUnivers3.defineButton(R.drawable.image_univers3_wait, false, this)
                    binding.ivUnivers3.contentDescription =
                        universAccessibility(
                            R.string.univers3_name,
                            R.string.univers_state_not_started,
                        )
                } else {
                    binding.ivUnivers3.defineButton(R.drawable.image_univers3_encours, true, this)
                    binding.ivUnivers3.contentDescription =
                        universAccessibility(R.string.univers3_name, R.string.univers_state_going)
                }
            }

            UniversStatus.GOING -> {
                binding.ivUnivers3.defineButton(R.drawable.image_univers3_encours, true, this)
                binding.ivUnivers3.contentDescription =
                    universAccessibility(R.string.univers3_name, R.string.univers_state_going)
                viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_3)?.let {
                    activityGoing = it
                }
            }

            UniversStatus.IS_FINISHED -> {
                binding.ivUnivers3.defineButton(R.drawable.image_univers3_finish, true, this)
                binding.ivUnivers3.contentDescription =
                    universAccessibility(R.string.univers3_name, R.string.univers_state_finished)
                activityGoing = GameStep.Result.name
            }
        }
        binding.ivUnivers3.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }
}
