package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySalleEmbarquementOneBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer

class SalleEmbarquementOneActivity : BaseActivity(), CadenaLayoutListener,
    SalleEmbarquementOneLayoutListener {

    private lateinit var binding: ActivitySalleEmbarquementOneBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_embarquement_one)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_1)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_1,
            LAST_ACTIVITY_LAUNCH.SALLE_EMBARQUEMENT_1
        )

        binding.viewpagerSalleEmbarquementOne.adapter =
            SalleEmbarquementOneViewPagerAdapter(this, this)
        binding.viewpagerSalleEmbarquementOne.setPageTransformer(true, ZoomOutPageTransformer())
        binding.viewpagerSalleEmbarquementOne.addOnPageChangeListener(
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

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "1990") {
            Alerts.showSuccess(this, ::launchNext, PopinType.UNLOCK_SUCCESS)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, SalleEmbarquementTwoActivity::class.java))
    }

    override fun onClickButtonHome() {
        startActivity(Intent(this, CarnetBordActivity::class.java))
    }
}