package com.pklein.mariage.presentation.salleDiner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SalleDinerOneLayout(val layoutResId: Int) {
    DINER1(R.layout.fragment_salle_diner_one_1),
    DINER2(R.layout.fragment_salle_diner_one_2),
}

interface SalleDinerOneListener {
    fun onClickImage(drawable: Int)
}

class SalleDinerOneViewPagerAdapter(
    private val mContext: Context,
    private val listener: SalleDinerOneListener
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerOneLayout.entries[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleDinerOneLayout.DINER1) {
            layout.findViewById<AppCompatImageView>(R.id.iv_diner_salle).setOnClickListener {
                listener.onClickImage(R.drawable.image_diner)
            }
        }

        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleDinerOneLayout.entries.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}