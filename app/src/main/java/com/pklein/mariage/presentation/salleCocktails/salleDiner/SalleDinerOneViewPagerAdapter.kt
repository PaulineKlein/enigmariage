package com.pklein.mariage.presentation.salleCocktails.salleDiner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
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
    private val playerName: String?,
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
        when (SalleDinerOneLayout.entries[position]) {
            SalleDinerOneLayout.DINER1 -> {
                holder
                    .itemView
                    .findViewById<AppCompatImageView>(R.id.iv_diner_salle)
                    .setOnClickListener {
                        listener.onClickImage(R.drawable.image_diner)
                    }
            }

            SalleDinerOneLayout.DINER2 -> {
                holder.itemView.findViewById<AppCompatTextView>(R.id.tv_diner_one_message_2).text =
                    holder.itemView.context.getString(R.string.diner_one_message_2, playerName)
                holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
            }
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
