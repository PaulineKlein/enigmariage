package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.ViewPagerDotsUtils
import kotlinx.android.synthetic.main.activity_introduction.*
import kotlinx.android.synthetic.main.activity_introduction.pager_dots
import kotlinx.android.synthetic.main.activity_salle_emarquement_one.*

class IntroductionActivity : AppCompatActivity(), IntroLayoutListener {
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.INTRODUCTION)

        viewpagerIntroduction.adapter = IntroductionViewPagerAdapter(this, this)
        viewpagerIntroduction.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList,2))
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList,2).setupDots(this)
        dotPagerList.forEach {
            pager_dots.addView(it)
        }
    }

    override fun onClickButton() {
        val intent = Intent(this, AddPlayerActivity::class.java)
        startActivity(intent)
    }
}
