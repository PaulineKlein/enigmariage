package com.pklein.mariage.presentation.salleCocktails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

private enum class SalleCocktailsSevenLayout(val layoutResId: Int) {
    COCKTAILS1(R.layout.fragment_salle_cocktails_seven_1),
    COCKTAILS2(R.layout.fragment_salle_cocktails_six_2),
}

interface SalleCocktailsSevenLayoutListener {
    fun onClickButton(response: String)
}

class SalleCocktailsSevenViewPagerAdapter(
    private val listener: SalleCocktailsSevenLayoutListener
) : RecyclerView.Adapter<SalleCocktailsSevenViewPagerAdapter.SalleCocktailsSevenViewHolder>(),
    CheckEmptyTextWatcherListener {
    private var submitButton: AppCompatButton? = null

    class SalleCocktailsSevenViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalleCocktailsSevenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(SalleCocktailsSevenLayout.entries[viewType].layoutResId, parent, false)
        return SalleCocktailsSevenViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalleCocktailsSevenViewHolder, position: Int) {
        if (SalleCocktailsSevenLayout.entries[position] == SalleCocktailsSevenLayout.COCKTAILS2) {
            val answer = holder.itemView.findViewById<AppCompatEditText>(R.id.et_question_answer)
            submitButton = holder.itemView.findViewById(R.id.button_cocktail_seven)
            answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
            submitButton?.setOnClickListener {
                listener.onClickButton(answer?.text.toString())
            }
        }
    }

    override fun getItemCount() = SalleCocktailsSevenLayout.entries.size

    override fun onTextEmpty() {
        submitButton?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        submitButton?.isEnabled = true
    }
}
