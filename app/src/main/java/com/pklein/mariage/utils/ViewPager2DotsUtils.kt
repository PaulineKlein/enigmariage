package com.pklein.mariage.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.pklein.mariage.R

class ViewPager2DotsUtils(private val pageCount: Int) : ViewPager2.OnPageChangeCallback() {
    private val dots: MutableList<ImageView> = mutableListOf()

    fun setupDots(context: Context): ViewPager2DotsUtils {
        dots.clear()
        for (i in 0 until pageCount) {
            dots.add(ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).also { it.setMargins(5, 0, 5, 0) }
                setImageResource(if (i == 0) R.drawable.dot_selected else R.drawable.dot_unselected)
            })
        }
        return this
    }

    fun addDotsTo(container: android.view.ViewGroup) {
        dots.forEach { container.addView(it) }
    }

    override fun onPageSelected(position: Int) {
        dots.forEachIndexed { index, dot ->
            dot.setImageResource(
                if (index == position) R.drawable.dot_selected else R.drawable.dot_unselected
            )
        }
    }
}
