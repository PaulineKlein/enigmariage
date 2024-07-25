package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SalleDinerCambodgeOneLayout(val layoutResId: Int) {
    CAMBODGE1(R.layout.fragment_salle_diner_cambodge_one_1),
    CAMBODGE2(R.layout.fragment_salle_diner_cambodge_one_2),
}

interface SalleDinerCambodgeOneListener {
    fun onClickImage(drawable: Int)
}

class SalleDinerCambodgeOneAdapter(
    private val mContext: Context,
    private val listener: SalleDinerCambodgeOneListener
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerCambodgeOneLayout.entries[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleDinerCambodgeOneLayout.CAMBODGE1) {
            layout.findViewById<AppCompatImageView>(R.id.iv_angkor).setOnClickListener {
                listener.onClickImage(R.drawable.image_angkor_couple)
            }
        }

        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleDinerCambodgeOneLayout.entries.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}