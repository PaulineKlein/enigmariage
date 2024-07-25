package com.pklein.mariage.presentation.sallePhotobooth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.pklein.mariage.R

enum class SallePhotoboothTwoLayout(val layoutResId: Int) {
    PHOTOBOOTH1(R.layout.fragment_salle_photobooth_two_1),
    PHOTOBOOTH2(R.layout.fragment_salle_photobooth_two_2),
}

interface SallePhotoboothTwoListener {
    fun onClickImage(drawable: Int)
}

class SallePhotoboothTwoAdapter(
    private val mContext: Context,
    private val listener: SallePhotoboothTwoListener
) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val modelObject = SallePhotoboothTwoLayout.entries[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId, collection, false) as ViewGroup

        if (modelObject == SallePhotoboothTwoLayout.PHOTOBOOTH1) {
            layout.findViewById<AppCompatImageView>(R.id.iv_photographe).setOnClickListener {
                listener.onClickImage(R.drawable.image_photographe)
            }
        }

        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return SallePhotoboothTwoLayout.entries.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}