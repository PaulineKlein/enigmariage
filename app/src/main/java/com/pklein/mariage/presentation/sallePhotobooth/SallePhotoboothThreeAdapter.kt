package com.pklein.mariage.presentation.sallePhotobooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CadenaLayout
import com.pklein.mariage.utils.uiUtils.CadenaLayoutListener

private enum class SallePhotoboothThreeLayout(
    val layoutResId: Int,
) {
    PHOTOBOOTH1(R.layout.fragment_salle_photobooth_three_1),
    PHOTOBOOTH2(R.layout.fragment_salle_photobooth_three_2),
}

interface SallePhotoboothThreeListener {
    fun onClickImage(drawable: Int)
}

class SallePhotoboothThreeAdapter(
    private val description: Int,
    private val enigmeImg: Int,
    private val listener: SallePhotoboothThreeListener,
    private val cadenaListener: CadenaLayoutListener,
) : RecyclerView.Adapter<SallePhotoboothThreeAdapter.SallePhotoboothThreeViewHolder>() {
    class SallePhotoboothThreeViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SallePhotoboothThreeViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(SallePhotoboothThreeLayout.entries[viewType].layoutResId, parent, false)
        return SallePhotoboothThreeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SallePhotoboothThreeViewHolder,
        position: Int,
    ) {
        val modelObject = SallePhotoboothThreeLayout.entries[position]
        if (modelObject == SallePhotoboothThreeLayout.PHOTOBOOTH1) {
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_photographe)?.apply {
                setImageResource(enigmeImg)
                setOnClickListener {
                    listener.onClickImage(enigmeImg)
                }
            }
        } else if (modelObject == SallePhotoboothThreeLayout.PHOTOBOOTH2) {
            holder.itemView
                .findViewById<AppCompatTextView>(R.id.tv_photobooth_two_message_3)
                ?.setText(description)
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun onViewAttachedToWindow(holder: SallePhotoboothThreeViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position =
            holder.bindingAdapterPosition
                .takeIf { it != RecyclerView.NO_POSITION } ?: return
        if (SallePhotoboothThreeLayout.entries[position] == SallePhotoboothThreeLayout.PHOTOBOOTH2) {
            holder.itemView.findViewById<CadenaLayout>(R.id.cadena)?.setListener(cadenaListener)
        }
    }

    override fun getItemCount() = SallePhotoboothThreeLayout.entries.size
}
