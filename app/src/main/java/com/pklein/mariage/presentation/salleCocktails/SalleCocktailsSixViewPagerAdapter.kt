package com.pklein.mariage.presentation.salleCocktails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

private enum class SalleCocktailsSixLayout(
    val layoutResId: Int,
) {
    COCKTAILS1(R.layout.fragment_salle_cocktails_six_1),
    COCKTAILS2(R.layout.fragment_salle_cocktails_six_2),
}

interface SalleCocktailsSixLayoutListener {
    fun onClickButton(response: String)
}

class SalleCocktailsSixViewPagerAdapter(
    private val playerName: String?,
    private val listener: SalleCocktailsSixLayoutListener,
) : RecyclerView.Adapter<SalleCocktailsSixViewPagerAdapter.SalleCocktailsSixViewHolder>(),
    CheckEmptyTextWatcherListener {
    private var submitButton: AppCompatButton? = null

    class SalleCocktailsSixViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SalleCocktailsSixViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(SalleCocktailsSixLayout.entries[viewType].layoutResId, parent, false)
        return SalleCocktailsSixViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SalleCocktailsSixViewHolder,
        position: Int,
    ) {
        when (SalleCocktailsSixLayout.entries[position]) {
            SalleCocktailsSixLayout.COCKTAILS1 -> {
                holder.itemView.findViewById<AppCompatTextView>(R.id.tv_cocktail_six_message_1).text =
                    holder.itemView.context.getString(R.string.cocktail_six_message_1, playerName)
            }

            SalleCocktailsSixLayout.COCKTAILS2 -> {
                val answer =
                    holder.itemView.findViewById<AppCompatEditText>(R.id.et_question_answer)
                submitButton = holder.itemView.findViewById(R.id.button_cocktail_six)
                answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
                submitButton?.setOnClickListener {
                    listener.onClickButton(answer?.text.toString())
                }
            }
        }
    }

    override fun getItemCount() = SalleCocktailsSixLayout.entries.size

    override fun onTextEmpty() {
        submitButton?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        submitButton?.isEnabled = true
    }
}
