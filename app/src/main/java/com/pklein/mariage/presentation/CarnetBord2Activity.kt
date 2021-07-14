package com.pklein.mariage.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UNIVERS_STATUS
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivityCarnetBord2Binding
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.extension.defineButton

class CarnetBord2Activity : BaseActivity() {

    private lateinit var binding: ActivityCarnetBord2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetBord2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.CARNET_BORD2)

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }

        showButton(
            SHARED_PREFERENCE_KEY.UNIVERS_2_CANADA,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CANADA_1,
            binding.buttonQuestionCanada,
            binding.ivMapmondeCanada,
            R.drawable.image_bouton_canada_encours,
            R.drawable.image_bouton_canada_finish
        )
        showButton(
            SHARED_PREFERENCE_KEY.UNIVERS_2_CAMBODGE,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_1,
            binding.buttonQuestionCambodge,
            binding.ivMapmondeCambodge,
            R.drawable.image_bouton_cambodge_encours,
            R.drawable.image_bouton_cambodge_finish
        )
        showButton(
            SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_1,
            binding.buttonQuestionJapon,
            binding.ivMapmondeJapon,
            R.drawable.image_bouton_japon_encours,
            R.drawable.image_bouton_japon_finish
        )
        if (isUnivers2Finish()) {
            binding.tvCarnetBravo.visibility = View.VISIBLE
            binding.tvCarnetBravo.setOnClickListener {
                startActivity(Intent(this, CarnetBordActivity::class.java))
            }
        } else {
            binding.tvCarnetBravo.visibility = View.INVISIBLE
        }
    }

    private fun showButton(
        universKey: SHARED_PREFERENCE_KEY,
        activityLaunch: LAST_ACTIVITY_LAUNCH,
        universButton: AppCompatImageView,
        ivMamonde: AppCompatImageView,
        drawableEncours: Int,
        drawableFinish: Int
    ) {
        val step = UniversViewModel.getUniversStatus(universKey)
        var activityGoing = activityLaunch.name
        when (step) {
            UNIVERS_STATUS.NOT_STARTED -> {
                universButton.defineButton(drawableEncours, true, this)
            }
            UNIVERS_STATUS.GOING -> {
                universButton.defineButton(drawableEncours, true, this)
                UniversViewModel.getUniversPage(universKey)?.let {
                    activityGoing = it
                }
            }
            UNIVERS_STATUS.IS_FINISHED -> {
                universButton.defineButton(drawableFinish, false, this)
                ivMamonde.visibility = View.VISIBLE
            }
        }

        universButton.setOnClickListener {
            SplashCoordinator.launchGame(activityGoing, this)
        }
    }

    private fun isUnivers2Finish(): Boolean {
        return !binding.buttonQuestionCanada.isEnabled &&
                !binding.buttonQuestionJapon.isEnabled &&
                !binding.buttonQuestionCambodge.isEnabled
    }
}