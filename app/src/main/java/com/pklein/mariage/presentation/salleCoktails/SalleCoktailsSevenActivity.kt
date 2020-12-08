package com.pklein.mariage.presentation.salleCoktails

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleCoktailsSevenBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.salleDiner.SalleDinerOneActivity
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts


class SalleCoktailsSevenActivity : BaseActivity(), SalleCoktailsSevenLayoutListener {

    private lateinit var binding: ActivitySalleCoktailsSevenBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_coktails_seven)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_COKTAIL_7)

        binding.viewpagerSalleCoktailsSeven.adapter = SalleCoktailsSevenViewPagerAdapter(this, this)
        binding.viewpagerSalleCoktailsSeven.addOnPageChangeListener(
            ViewPagerDotsUtils(
                dotPagerList,
                1
            )
        )
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 1).setupDots(this)
        dotPagerList.forEach {
            binding.pagerDots.addView(it)
        }
    }

    override fun onClickButton(response: String) {
        if (response.formatAnswer() == "dinera") {
            Alerts.showSuccess(this, ::launchNext)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleDinerOneActivity::class.java))
    }
}