package com.pklein.mariage.presentation.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.pklein.mariage.data.SharedPreferenceStored
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.databinding.ActivityIntroductionBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.GameProgressViewModel
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.utils.ViewPager2DotsUtils
import com.pklein.mariage.utils.extension.addSystemWindowInsetToMargin
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity :
    BaseActivity(),
    IntroLayoutListener {
    private lateinit var binding: ActivityIntroductionBinding
    private val gameProgress: GameProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceStored.resetPreference()
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameProgress.storePage(GameStep.Introduction)
        gameProgress.storeStartTime()

        val adapter = IntroductionViewPagerAdapter(this)
        val dotsUtils = ViewPager2DotsUtils(adapter.itemCount).setupDots(this)
        dotsUtils.addDotsTo(binding.pagerDots)
        binding.viewpagerIntroduction.adapter = adapter
        binding.viewpagerIntroduction.setPageTransformer(ZoomOutPageTransformer())
        binding.viewpagerIntroduction.registerOnPageChangeCallback(dotsUtils)
        launchCountDown()
        binding.root.addSystemWindowInsetToMargin()
    }

    override fun onClickButton() {
        startActivity(Intent(this, AddPlayerActivity::class.java))
    }

    override fun onNoxTapped() {
        if (!gameProgress.isTrophyUnlocked(Trophies.TROPHY_DONT_TOUCH)) {
            gameProgress.saveTrophy(Trophies.TROPHY_DONT_TOUCH)
            Alerts.showTrophyUnlocked(this, getString(Trophies.TROPHY_DONT_TOUCH.message))
        }
    }
}
