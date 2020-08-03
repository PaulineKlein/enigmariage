package com.pklein.mariage.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.pklein.mariage.R

class ViewPagerDotsUtils(
    private val dotPagerList: MutableList<ImageView>,
    private val DotsSize: Int
) : ViewPager.OnPageChangeListener {
    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        // nothing to do
    }

    override fun onPageSelected(position: Int) {
        for (i in 0..DotsSize) {
            dotPagerList[i].setImageResource(R.drawable.dot_unselected)
        }
        dotPagerList[position].setImageResource(R.drawable.dot_selected)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    fun setupDots(context: Context): MutableList<ImageView> {
        for (i in 0..DotsSize) {
            dotPagerList.add(ImageView(context))
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(5, 0, 5, 0)
            dotPagerList[i].layoutParams = params
            if (i == 0) {
                dotPagerList[i].setImageResource(R.drawable.dot_selected)
            } else {
                dotPagerList[i].setImageResource(R.drawable.dot_unselected)
            }
        }
        return dotPagerList
    }
}