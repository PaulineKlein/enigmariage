package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivitySalleDinerCambodgeFiveBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.sallePhotobooth.SallePhotoboothOneActivity
import com.pklein.mariage.utils.ViewPagerDotsUtils
import com.pklein.mariage.utils.uiUtils.ZoomOutPageTransformer

class SalleDinerCambodgeFiveActivity : BaseActivity(), SalleDinerCambodgeFiveLayoutListener {

    private lateinit var binding: ActivitySalleDinerCambodgeFiveBinding
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salle_diner_cambodge_five)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_5)

        binding.viewpagerSalleCambodgeFive.adapter = SalleDinerCambodgeFiveAdapter(this, this)
        binding.viewpagerSalleCambodgeFive.setPageTransformer(true, ZoomOutPageTransformer())
        binding.viewpagerSalleCambodgeFive.addOnPageChangeListener(
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

    override fun onClickButton() {
        startActivity(Intent(this, SallePhotoboothOneActivity::class.java))
    }
}