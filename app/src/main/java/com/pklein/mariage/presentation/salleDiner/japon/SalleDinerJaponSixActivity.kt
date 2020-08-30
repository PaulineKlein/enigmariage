package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import kotlinx.android.synthetic.main.activity_salle_diner_japon_six.*
import kotlinx.android.synthetic.main.activity_salle_emarquement_one.pager_dots

class SalleDinerJaponSixActivity : AppCompatActivity(), SalleDinerJaponSixLayoutListener {
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_japon_six)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_6)

        viewpagerSalleJaponSix.adapter = SalleDinerJaponSixViewPagerAdapter(this, this)
        viewpagerSalleJaponSix.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 1))
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 1).setupDots(this)
        dotPagerList.forEach {
            pager_dots.addView(it)
        }
    }

    override fun onClickButton(response: String) {
        if (response.formatAnswer() == "rendez vous au cambodge" || response.formatAnswer() == "rendez-vous au cambodge") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerJaponSixActivity::class.java))
    }
}