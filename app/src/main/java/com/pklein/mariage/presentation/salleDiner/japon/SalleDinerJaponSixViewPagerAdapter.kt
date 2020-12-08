package com.pklein.mariage.presentation.salleDiner.japon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

enum class SalleDinerJaponSixLayout(val layoutResId: Int) {
    DINER1(R.layout.fragment_salle_diner_japon_six_1),
    DINER2(R.layout.fragment_salle_diner_japon_six_2),
}

interface SalleDinerJaponSixLayoutListener {
    fun onClickButton(response: String)
}

class SalleDinerJaponSixViewPagerAdapter(
    private val mContext: Context,
    private val listener: SalleDinerJaponSixLayoutListener
) : PagerAdapter(), CheckEmptyTextWatcherListener {

    lateinit var layout: ViewGroup

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SalleDinerJaponSixLayout.values()[position]
        val inflater = LayoutInflater.from(mContext)
        layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SalleDinerJaponSixLayout.DINER2) {
            val answer = layout.findViewById<AppCompatEditText>(R.id.et_diner_japon_six_answer)
            answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
            layout.findViewById<AppCompatButton>(R.id.button_diner_japon_six)?.setOnClickListener {
                listener.onClickButton(answer?.text.toString())
            }
        }
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SalleDinerJaponSixLayout.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun onTextEmpty() {
        layout.findViewById<AppCompatButton>(R.id.button_diner_japon_six)?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        layout.findViewById<AppCompatButton>(R.id.button_diner_japon_six)?.isEnabled = true
    }
}