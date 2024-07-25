package com.pklein.mariage.presentation.salleCoktails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R
import com.pklein.mariage.presentation.salleEmbarquement.SalleEmbarquementOneLayoutListener

enum class SalleCoktailsOneLayout(val layoutResId: Int) {
    COCKTAIL1(R.layout.fragment_salle_coktails_one_1),
    COCKTAIL2(R.layout.fragment_salle_coktails_one_2),
}

interface SalleCoktailsOneLayoutListener {
    fun onClickButtonHome()
    fun onClickImage(drawable: Int)
}

class SalleCoktailsOneViewPagerAdapter(
    private val mContext: Context,
    private val listener: SalleCoktailsOneLayoutListener
) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleCoktailsOneLayout.entries[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleCoktailsOneLayout.COCKTAIL1) {
            layout.findViewById<AppCompatImageView>(R.id.iv_home).setOnClickListener {
                listener.onClickButtonHome()
            }
            layout.findViewById<AppCompatImageView>(R.id.iv_coktail_one).setOnClickListener {
                listener.onClickImage(R.drawable.image_number_two)
            }
        }

        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleCoktailsOneLayout.entries.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}