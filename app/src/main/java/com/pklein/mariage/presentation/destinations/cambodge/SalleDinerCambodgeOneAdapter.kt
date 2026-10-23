package com.pklein.mariage.presentation.destinations.cambodge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CadenaLayout
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener

private enum class SalleDinerCambodgeOneLayout(val layoutResId: Int) {
    CAMBODGE1(R.layout.fragment_salle_diner_cambodge_one_1),
    CAMBODGE2(R.layout.fragment_salle_diner_cambodge_one_2),
}

interface SalleDinerCambodgeOneListener {
    fun onClickImage(drawable: Int)
}

class SalleDinerCambodgeOneAdapter(
    private val listener: SalleDinerCambodgeOneListener,
    private val cadenaListener: CadenaLayoutListener
) : RecyclerView.Adapter<SalleDinerCambodgeOneAdapter.SalleDinerCambodgeOneViewHolder>() {
    class SalleDinerCambodgeOneViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalleDinerCambodgeOneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(SalleDinerCambodgeOneLayout.entries[viewType].layoutResId, parent, false)
        return SalleDinerCambodgeOneViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalleDinerCambodgeOneViewHolder, position: Int) {
        val modelObject = SalleDinerCambodgeOneLayout.entries[position]
        if (modelObject == SalleDinerCambodgeOneLayout.CAMBODGE1) {
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_angkor).setOnClickListener {
                listener.onClickImage(R.drawable.image_angkor_couple)
            }
        } else if (modelObject == SalleDinerCambodgeOneLayout.CAMBODGE2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun onViewAttachedToWindow(holder: SalleDinerCambodgeOneViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.bindingAdapterPosition
            .takeIf { it != RecyclerView.NO_POSITION } ?: return
        if (SalleDinerCambodgeOneLayout.entries[position] == SalleDinerCambodgeOneLayout.CAMBODGE2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun getItemCount() = SalleDinerCambodgeOneLayout.entries.size
}
