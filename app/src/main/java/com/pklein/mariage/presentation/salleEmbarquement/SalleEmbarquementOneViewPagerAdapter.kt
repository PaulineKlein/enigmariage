package com.pklein.mariage.presentation.salleEmbarquement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SalleEmbarquementOneLayout(val layoutResId: Int) {
    EMBARQUEMENT1(R.layout.fragment_salle_emarquement_one_1),
    EMBARQUEMENT2(R.layout.fragment_salle_emarquement_one_2),
}

class SalleEmbarquementOneViewPagerAdapter(
    private val mContext: Context
) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleEmbarquementOneLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleEmbarquementOneLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}