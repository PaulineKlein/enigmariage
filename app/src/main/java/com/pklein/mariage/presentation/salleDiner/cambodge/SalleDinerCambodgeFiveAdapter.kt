package com.pklein.mariage.presentation.salleDiner.cambodge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R
import kotlinx.android.synthetic.main.fragment_salle_diner_cambodge_five_2.view.*

enum class SalleDinerCambodgeFiveLayout(val layoutResId: Int) {
    CAMBODGE1(R.layout.fragment_salle_diner_cambodge_five_1),
    CAMBODGE2(R.layout.fragment_salle_diner_cambodge_five_2),
}

interface SalleDinerCambodgeFiveLayoutListener {
    fun onClickButton()
}

class SalleDinerCambodgeFiveAdapter(
    private val mContext: Context,
    private val listener: SalleDinerCambodgeFiveLayoutListener
) : PagerAdapter() {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerCambodgeFiveLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleDinerCambodgeFiveLayout.CAMBODGE2) {
            layout.button_cambodge_five?.setOnClickListener {
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
        return SalleDinerCambodgeFiveLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}