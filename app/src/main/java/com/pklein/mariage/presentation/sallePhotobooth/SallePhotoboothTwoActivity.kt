package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.combatFinal.CombatFinalOneActivity
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType
import kotlinx.android.synthetic.main.activity_salle_photobooth_two.*


class SallePhotoboothTwoActivity : BaseActivity(), CadenaLayoutListener {
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_photobooth_two)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_2)

        viewpagerSallePhotoboothTwo.adapter = SallePhotoboothTwoAdapter(this)
        viewpagerSallePhotoboothTwo.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 1))
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 1).setupDots(this)
        dotPagerList.forEach {
            pager_dots.addView(it)
        }
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "2021") {
            Alerts.showSuccess(this, ::launchNext, PopinType.UNLOCK_SUCCESS)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, CombatFinalOneActivity::class.java))
    }
}