package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.utils.ViewPagerDotsUtils
import kotlinx.android.synthetic.main.activity_salle_diner_cambodge_five.*

class SalleDinerCambodgeFiveActivity : AppCompatActivity(), SalleDinerCambodgeFiveLayoutListener {
    private var dotPagerList: MutableList<ImageView> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salle_diner_cambodge_five)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.SALLE_DINER_CAMBODGE_5)

        viewpagerSalleCambodgeFive.adapter = SalleDinerCambodgeFiveAdapter(this, this)
        viewpagerSalleCambodgeFive.addOnPageChangeListener(ViewPagerDotsUtils(dotPagerList, 1))
        setupDots()
    }

    private fun setupDots() {
        dotPagerList = ViewPagerDotsUtils(dotPagerList, 1).setupDots(this)
        dotPagerList.forEach {
            pager_dots.addView(it)
        }
    }

    override fun onClickButton() {
        startActivity(Intent(this, SalleDinerCambodgeFiveActivity::class.java))
    }
}