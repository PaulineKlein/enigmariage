package com.pklein.mariage.presentation

import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UNIVERS_STATUS
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityCarnetBordBinding
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.defineButton

class CarnetBordActivity : BaseActivity() {
    lateinit var binding: ActivityCarnetBordBinding
    private var step1 = UNIVERS_STATUS.NOT_STARTED
    private var step2 = UNIVERS_STATUS.NOT_STARTED
    private var step3 = UNIVERS_STATUS.NOT_STARTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetBordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.CARNET_BORD)

        showUnivers1()
        showUnivers2()
        showUnivers3()
    }

    private fun showUnivers1() {
        step1 = UniversViewModel.getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_1)
        var activityGoing = LAST_ACTIVITY_LAUNCH.LOCALISATION_BATEAU_1.name
        when (step1) {
            UNIVERS_STATUS.NOT_STARTED -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_encours, true, this)
                binding.cvUnivers1.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
            }
            UNIVERS_STATUS.GOING -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_encours, true, this)
                binding.cvUnivers1.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
                UniversViewModel.getUniversPage(SHARED_PREFERENCE_KEY.UNIVERS_1)?.let {
                    activityGoing = it
                }
            }
            UNIVERS_STATUS.IS_FINISHED -> {
                binding.ivUnivers1.defineButton(R.drawable.image_univers1_finish, false, this)
                binding.cvUnivers1.elevation = resources.getDimension(R.dimen.carnet_bord_flat)
            }
        }

        binding.ivUnivers1.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun showUnivers2() {
        step2 = UniversViewModel.getUnivers2FinalStatus()
        var activityGoing = LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_1.name
        when (step2) {
            UNIVERS_STATUS.NOT_STARTED -> {
                if (step1 != UNIVERS_STATUS.IS_FINISHED) {
                    binding.ivUnivers2.defineButton(R.drawable.image_univers2_wait, false, this)
                    binding.cvUnivers2.elevation = resources.getDimension(R.dimen.carnet_bord_flat)
                } else {
                    binding.ivUnivers2.defineButton(R.drawable.image_univers2_encours, true, this)
                    binding.cvUnivers2.elevation =
                        resources.getDimension(R.dimen.carnet_bord_elevated)
                }
            }
            UNIVERS_STATUS.GOING -> {
                binding.ivUnivers2.defineButton(R.drawable.image_univers2_encours, true, this)
                binding.cvUnivers2.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
                activityGoing = UniversViewModel.getUnivers2Page()
            }
            UNIVERS_STATUS.IS_FINISHED -> {
                binding.ivUnivers2.defineButton(R.drawable.image_univers2_finish, true, this)
                binding.cvUnivers2.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
                activityGoing = LAST_ACTIVITY_LAUNCH.CARNET_BORD2.name
            }
        }

        binding.ivUnivers2.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun showUnivers3() {
        step3 = UniversViewModel.getUniversStatus(SHARED_PREFERENCE_KEY.UNIVERS_3)
        var activityGoing = LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_0.name
        when (step3) {
            UNIVERS_STATUS.NOT_STARTED -> {
                if (step2 != UNIVERS_STATUS.IS_FINISHED) {
                    binding.ivUnivers3.defineButton(R.drawable.image_univers3_wait, false, this)
                    binding.cvUnivers3.elevation = resources.getDimension(R.dimen.carnet_bord_flat)
                } else {
                    binding.ivUnivers3.defineButton(R.drawable.image_univers3_encours, true, this)
                    binding.cvUnivers3.elevation =
                        resources.getDimension(R.dimen.carnet_bord_elevated)
                }
            }
            UNIVERS_STATUS.GOING -> {
                binding.ivUnivers3.defineButton(R.drawable.image_univers3_encours, true, this)
                binding.cvUnivers3.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
                UniversViewModel.getUniversPage(SHARED_PREFERENCE_KEY.UNIVERS_3)?.let {
                    activityGoing = it
                }
            }
            UNIVERS_STATUS.IS_FINISHED -> {
                binding.ivUnivers3.defineButton(R.drawable.image_univers3_finish, true, this)
                binding.cvUnivers3.elevation = resources.getDimension(R.dimen.carnet_bord_elevated)
                activityGoing = LAST_ACTIVITY_LAUNCH.RESULT.name
            }
        }
        binding.ivUnivers3.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }
}