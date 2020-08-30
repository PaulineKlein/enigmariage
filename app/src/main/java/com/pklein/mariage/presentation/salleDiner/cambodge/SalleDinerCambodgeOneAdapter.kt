package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SalleDinerCambodgeOneLayout(val layoutResId: Int) {
    CAMBODGE1(R.layout.fragment_salle_diner_cambodge_one_1),
    CAMBODGE2(R.layout.fragment_salle_diner_cambodge_one_2),
}

class SalleDinerCambodgeOneAdapter(
    private val mContext: Context
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerCambodgeOneLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleDinerCambodgeOneLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}