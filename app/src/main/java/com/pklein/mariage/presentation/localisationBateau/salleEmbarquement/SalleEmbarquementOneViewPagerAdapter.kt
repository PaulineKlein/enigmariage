package com.pklein.mariage.presentation.localisationBateau.salleEmbarquement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CadenaLayout
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener

private enum class SalleEmbarquementOneLayout(val layoutResId: Int) {
    EMBARQUEMENT1(R.layout.fragment_salle_emarquement_one_1),
    EMBARQUEMENT2(R.layout.fragment_salle_emarquement_one_2),
}

interface SalleEmbarquementOneLayoutListener {
    fun onClickButtonHome()
    fun onClickImage(drawable: Int)
}

class SalleEmbarquementOneViewPagerAdapter(
    private val listener: SalleEmbarquementOneLayoutListener,
    private val cadenaListener: CadenaLayoutListener,
    private val description: CharSequence,
    private val enigmeImage: Int,
    private val enigmeAccessibility: String
) : RecyclerView.Adapter<SalleEmbarquementOneViewPagerAdapter.SalleEmbarquementOneViewHolder>() {
    class SalleEmbarquementOneViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalleEmbarquementOneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(SalleEmbarquementOneLayout.entries[viewType].layoutResId, parent, false)
        return SalleEmbarquementOneViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalleEmbarquementOneViewHolder, position: Int) {
        val modelObject = SalleEmbarquementOneLayout.entries[position]
        if (modelObject == SalleEmbarquementOneLayout.EMBARQUEMENT1) {
            holder
                .itemView
                .findViewById<AppCompatTextView>(R.id.tv_embarquement_one_message_1)
                .text = description

            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_home).setOnClickListener {
                listener.onClickButtonHome()
            }
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_embarquement).apply {
                setImageDrawable(AppCompatResources.getDrawable(context, enigmeImage))
                contentDescription = enigmeAccessibility
                setOnClickListener {
                    listener.onClickImage(enigmeImage)
                }
            }

        } else if (modelObject == SalleEmbarquementOneLayout.EMBARQUEMENT2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun onViewAttachedToWindow(holder: SalleEmbarquementOneViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.bindingAdapterPosition
            .takeIf { it != RecyclerView.NO_POSITION } ?: return
        if (SalleEmbarquementOneLayout.entries[position] == SalleEmbarquementOneLayout.EMBARQUEMENT2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun getItemCount() = SalleEmbarquementOneLayout.entries.size
}
