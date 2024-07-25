package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.data.UniversViewModel
import com.pklein.mariage.databinding.ActivitySallePhotoboothTwoBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.CarnetBordActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.combatFinal.CombatFinalOneActivity
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.extension.formatAnswer
import com.pklein.mariage.utils.extension.showImageWithStfalconViewer
import com.pklein.mariage.utils.uiUtils.Alerts
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener
import com.pklein.mariage.utils.uiUtils.PopinType
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer


class SallePhotoboothTwoActivity : BaseActivity(), CadenaLayoutListener,
    SallePhotoboothTwoListener {

    private lateinit var binding: ActivitySallePhotoboothTwoBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_photobooth_two)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_2)
        UniversViewModel.storeUniversPage(
            SHARED_PREFERENCE_KEY.UNIVERS_3,
            LAST_ACTIVITY_LAUNCH.SALLE_PHOTOBOOTH_2
        )
        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, CarnetBordActivity::class.java))
        }
        binding.viewpagerSallePhotoboothTwo.adapter = SallePhotoboothTwoAdapter(this, this)
        binding.viewpagerSallePhotoboothTwo.setPageTransformer(true, ZoomOutPageTransformer())
        binding.viewpagerSallePhotoboothTwo.addOnPageChangeListener(
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

    override fun onClickImage(drawable: Int) {
        showImageWithStfalconViewer(this, drawable)
    }

    override fun onValidateCLicked(response: String) {
        if (response.formatAnswer() == "50") {
            Alerts.showSuccess(this, ::launchNext, PopinType.UNLOCK_SUCCESS)
        } else {
            Alerts.showError(this)
        }
    }

    private fun launchNext() {
        startActivity(Intent(this, CombatFinalOneActivity::class.java))
    }
}