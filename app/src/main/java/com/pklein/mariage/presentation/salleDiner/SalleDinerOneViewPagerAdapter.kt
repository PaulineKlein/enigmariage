package com.pklein.mariage.presentation.salleDiner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SalleDinerOneLayout(val layoutResId: Int) {
    DINER1(R.layout.fragment_salle_diner_one_1),
    DINER2(R.layout.fragment_salle_diner_one_2),
}

class SalleDinerOneViewPagerAdapter(
    private val mContext: Context
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerOneLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleDinerOneLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}