package com.pklein.mariage.presentation.salleCocktails.salleDiner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CadenaLayout
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener

private enum class SalleDinerOneLayout(
    val layoutResId: Int,
) {
    DINER1(R.layout.fragment_salle_diner_one_1),
    DINER2(R.layout.fragment_salle_diner_one_2),
}

interface SalleDinerOneListener {
    fun onClickImage(drawable: Int)
}

class SalleDinerOneViewPagerAdapter(
    private val listener: SalleDinerOneListener,
    private val cadenaListener: CadenaLayoutListener,
) : RecyclerView.Adapter<SalleDinerOneViewPagerAdapter.SalleDinerOneViewHolder>() {
    class SalleDinerOneViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SalleDinerOneViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(SalleDinerOneLayout.entries[viewType].layoutResId, parent, false)
        return SalleDinerOneViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SalleDinerOneViewHolder,
        position: Int,
    ) {
        val modelObject = SalleDinerOneLayout.entries[position]
        if (modelObject == SalleDinerOneLayout.DINER1) {
            holder
                .itemView
                .findViewById<AppCompatImageView>(R.id.iv_diner_salle)
                .setOnClickListener {
                    listener.onClickImage(R.drawable.image_diner)
                }
        } else if (modelObject == SalleDinerOneLayout.DINER2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun onViewAttachedToWindow(holder: SalleDinerOneViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position =
            holder.bindingAdapterPosition
                .takeIf { it != RecyclerView.NO_POSITION } ?: return
        if (SalleDinerOneLayout.entries[position] == SalleDinerOneLayout.DINER2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun getItemCount() = SalleDinerOneLayout.entries.size
}
