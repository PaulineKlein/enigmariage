package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerJaponSixBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBord2Activity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer

class SalleDinerJaponSixActivity : BaseActivity(), SalleDinerJaponSixLayoutListener {
    private lateinit var binding: ActivitySalleDinerJaponSixBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_diner_japon_six)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_6)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON,
            LAST_ACTIVITY_LAUNCH.SALLE_DINER_JAPON_6
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBord2Activity::class.java))
        }

        binding.viewpagerSalleJaponSix.adapter = SalleDinerJaponSixViewPagerAdapter(this, this)
        binding.viewpagerSalleJaponSix.setPageTransformer(true, ZoomOutPageTransformer())
        binding.viewpagerSalleJaponSix.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 1))
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 1).setupDots(this)
        dotPagerList.forEach {
            binding.pagerDots.addView(it)
        }
    }

    override fun onClickButton(response: String) {
        if (response.formatAnswer() == "rendez vous au cambodge" || response.formatAnswer() == "rendez-vous au cambodge") {
            Alerts.showPopup(
                this,
                getString(R.string.diner_japon_six_bravo),
                ::launchNext,
                PopinType.CLAPPING
            )
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        UniversViewModel.finishUnivers(SHARED_PREFERENCE_KEY.UNIVERS_2_JAPON)
        startActivity(Intent(this, CarnetBord2Activity::class.java))
    }
}