package com.pklein.mariage.presentation.introduction

import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R

private enum class IntroLayout(val layoutResId: Int) {
    INTRO1(R.layout.fragment_introduction_1),
    INTRO2(R.layout.fragment_introduction_2),
    INTRO3(R.layout.fragment_introduction_3)
}

interface IntroLayoutListener {
    fun onClickButton()
    fun onNoxTapped()
}

private const val NOX_MAX_COUNT = 5

class IntroductionViewPagerAdapter(
    private val listener: IntroLayoutListener
) : RecyclerView.Adapter<IntroductionViewPagerAdapter.IntroViewHolder>() {
    class IntroViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var noxTapCount = 0

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(IntroLayout.entries[viewType].layoutResId, parent, false)
        return IntroViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        when (IntroLayout.entries[position]) {
            IntroLayout.INTRO1 -> {
                holder.itemView.findViewById<View>(R.id.iv_introduction_nox)
                    ?.setOnClickListener {
                        it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        noxTapCount++
                        if (noxTapCount == NOX_MAX_COUNT) listener.onNoxTapped()
                    }
            }

            IntroLayout.INTRO3 -> {
                holder.itemView.findViewById<AppCompatButton>(R.id.button_introduction)
                    ?.setOnClickListener { listener.onClickButton() }
            }

            else -> Unit
        }
    }

    override fun getItemCount() = IntroLayout.entries.size
}
