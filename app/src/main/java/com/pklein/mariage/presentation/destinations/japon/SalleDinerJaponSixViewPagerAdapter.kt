package com.pklein.mariage.presentation.destinations.japon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcher
import com.pklein.mariage.utils.uiUtils.CheckEmptyTextWatcherListener

private enum class SalleDinerJaponSixLayout(val layoutResId: Int) {
    DINER1(R.layout.fragment_salle_diner_japon_six_1),
    DINER2(R.layout.fragment_salle_diner_japon_six_2),
}

interface SalleDinerJaponSixLayoutListener {
    fun onClickButton(response: String)
}

class SalleDinerJaponSixViewPagerAdapter(
    private val listener: SalleDinerJaponSixLayoutListener
) : RecyclerView.Adapter<SalleDinerJaponSixViewPagerAdapter.SalleDinerJaponSixViewHolder>(),
    CheckEmptyTextWatcherListener {
    private var submitButton: AppCompatButton? = null

    class SalleDinerJaponSixViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SalleDinerJaponSixViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(SalleDinerJaponSixLayout.entries[viewType].layoutResId, parent, false)
        return SalleDinerJaponSixViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalleDinerJaponSixViewHolder, position: Int) {
        if (SalleDinerJaponSixLayout.entries[position] == SalleDinerJaponSixLayout.DINER2) {
            val answer =
                holder.itemView.findViewById<AppCompatEditText>(R.id.et_diner_japon_six_answer)
            submitButton = holder.itemView.findViewById(R.id.button_diner_japon_six)
            answer?.addTextChangedListener(CheckEmptyTextWatcher(this))
            submitButton?.setOnClickListener {
                listener.onClickButton(answer?.text.toString())
            }
        }
    }

    override fun getItemCount() = SalleDinerJaponSixLayout.entries.size

    override fun onTextEmpty() {
        submitButton?.isEnabled = false
    }

    override fun onTextNotEmpty() {
        submitButton?.isEnabled = true
    }
}
