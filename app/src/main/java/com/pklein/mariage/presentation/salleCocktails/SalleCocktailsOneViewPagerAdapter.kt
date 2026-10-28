package com.pklein.mariage.presentation.salleCocktails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CadenaLayout
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener

private enum class SalleCocktailsOneLayout(
    val layoutResId: Int,
) {
    COCKTAIL1(R.layout.fragment_salle_cocktails_one_1),
    COCKTAIL2(R.layout.fragment_salle_cocktails_one_2),
}

interface SalleCocktailsOneLayoutListener {
    fun onClickButtonHome()

    fun onClickImage(drawable: Int)
}

class SalleCocktailsOneViewPagerAdapter(
    private val playerName: String?,
    private val listener: SalleCocktailsOneLayoutListener,
    private val cadenaListener: CadenaLayoutListener,
) : RecyclerView.Adapter<SalleCocktailsOneViewPagerAdapter.SalleCocktailsOneViewHolder>() {
    class SalleCocktailsOneViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SalleCocktailsOneViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(SalleCocktailsOneLayout.entries[viewType].layoutResId, parent, false)
        return SalleCocktailsOneViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SalleCocktailsOneViewHolder,
        position: Int,
    ) {
        val modelObject = SalleCocktailsOneLayout.entries[position]
        if (modelObject == SalleCocktailsOneLayout.COCKTAIL1) {
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_home).setOnClickListener {
                listener.onClickButtonHome()
            }
            holder.itemView
                .findViewById<AppCompatImageView>(R.id.iv_cocktail_one)
                .setOnClickListener {
                    listener.onClickImage(R.drawable.image_number_two)
                }
        } else if (modelObject == SalleCocktailsOneLayout.COCKTAIL2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
            holder.itemView.findViewById<AppCompatTextView>(R.id.tv_cocktail_one_message)?.text =
                holder.itemView.context.getString(R.string.cocktail_one_question, playerName)
        }
    }

    override fun onViewAttachedToWindow(holder: SalleCocktailsOneViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position =
            holder.bindingAdapterPosition
                .takeIf { it != RecyclerView.NO_POSITION } ?: return
        if (SalleCocktailsOneLayout.entries[position] == SalleCocktailsOneLayout.COCKTAIL2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun getItemCount() = SalleCocktailsOneLayout.entries.size
}
