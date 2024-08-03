package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivityIntroductionBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.currentTimeToString
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer

class IntroductionActivity : BaseActivity(), IntroLayoutListener {

    private lateinit var binding: ActivityIntroductionBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceStored.resetPreference()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.INTRODUCTION)
        SharedPreferenceStored.storeValue(
            SHARED_PREFERENCE_KEY.PLAYER_START_TIME,
            currentTimeToString()
        )

        binding.viewpagerIntroduction.adapter = IntroductionViewPagerAdapter(this, this)
        binding.viewpagerIntroduction.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 2))
        binding.viewpagerIntroduction.setPageTransformer(true, ZoomOutPageTransformer())
        setupDots()
        launchCountDown()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 2).setupDots(this)
        dotPagerList.forEach {
            binding.pagerDots.addView(it)
        }
    }

    override fun onClickButton() {
        startActivity(Intent(this, AddPlayerActivity::class.java))
    }
}
