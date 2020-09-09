package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.currentTimeToString
import kotlinx.android.synthetic.main.activity_introduction.*

class IntroductionActivity : BaseActivity(), IntroLayoutListener {
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.INTRODUCTION)
        SharedPreferenceStored.storeValue(
            SHARED_PREFERENCE_KEY.PLAYER_START_TIME,
            currentTimeToString()
        )

        viewpagerIntroduction.adapter = IntroductionViewPagerAdapter(this, this)
        viewpagerIntroduction.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 2))
        setupDots()
        launchCountDown()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 2).setupDots(this)
        dotPagerList.forEach {
            pager_dots.addView(it)
        }
    }

    override fun onClickButton() {
        startActivity(Intent(this, AddPlayerActivity::class.java))
    }
}
