package com.pklein.mariage.presentation.introduction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R
import com.pklein.mariage.databinding.ActivityIntroductionBinding

enum class IntroLayout(val layoutResId: Int) {
    INTRO1(R.layout.fragment_introduction_1),
    INTRO2(R.layout.fragment_introduction_2),
    INTRO3(R.layout.fragment_introduction_3)
}

interface IntroLayoutListener {
    fun onClickButton()
}

class IntroductionViewPagerAdapter(private val mContext: Context, private val listener: IntroLayoutListener) : PagerAdapter() {
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = IntroLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        if(modelObject == IntroLayout.INTRO3 ) {
            layout.findViewById<AppCompatButton>(R.id.button_introduction)?.setOnClickListener {
                listener.onClickButton()
            }
        }
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return IntroLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}