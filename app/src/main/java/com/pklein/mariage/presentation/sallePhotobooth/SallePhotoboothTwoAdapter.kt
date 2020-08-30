package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SallePhotoboothTwoLayout(val layoutResId: Int) {
    PHOTOBOOTH1(R.layout.fragment_salle_photobooth_two_1),
    PHOTOBOOTH2(R.layout.fragment_salle_photobooth_two_2),
}

class SallePhotoboothTwoAdapter(
    private val mContext: Context
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SallePhotoboothTwoLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SallePhotoboothTwoLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}