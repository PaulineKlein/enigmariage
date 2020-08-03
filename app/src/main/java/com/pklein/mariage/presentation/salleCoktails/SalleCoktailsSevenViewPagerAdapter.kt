package com.pklein.mariage.presentation.salleCoktails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener
import kotlinx.android.synthetic.main.fragment_salle_coktails_seven_2.view.*

enum class SalleCoktailsSevenLayout(val layoutResId: Int) {
    COCKTAILS1(R.layout.fragment_salle_coktails_seven_1),
    COCKTAILS2(R.layout.fragment_salle_coktails_seven_2),
}

interface SalleCoktailsSevenLayoutListener {
    fun onClickButton(response: String)
}

class SalleCoktailsSevenViewPagerAdapter(
    private val mContext: Context,
    private val listener: SalleCoktailsSevenLayoutListener
) : PagerAdapter(), CheckEmptyTextWatcherListener {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleCoktailsSevenLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleCoktailsSevenLayout.COCKTAILS2) {
            layout.et_question_answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
            layout.button_cocktail_seven?.setOnClickListener {
                listener.onClickButton(layout.et_question_answer?.text.toString())
            }
        }
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleCoktailsSevenLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun onTextEmpty() {
        layout.button_cocktail_seven?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        layout.button_cocktail_seven?.isEnabled = true
    }
}