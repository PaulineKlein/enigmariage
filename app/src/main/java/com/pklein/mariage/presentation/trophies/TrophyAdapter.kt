package com.pklein.mariage.presentation.trophies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pklein.mariage.R
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.model.TrophyState
import com.pklein.mariage.databinding.ItemTrophyBinding

class TrophyAdapter(
    private val items: List<Pair<Trophies, Boolean>>
) : RecyclerView.Adapter<TrophyAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemTrophyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trophy: Trophies, unlocked: Boolean) {
            val ctx = binding.root.context
            if (unlocked) {
                binding.ivTrophyItemIcon.setImageResource(R.drawable.ic_trophy)
                binding.ivTrophyItemIcon.imageTintList = when (trophy.state) {
                    TrophyState.BRONZE -> ContextCompat.getColorStateList(
                        ctx,
                        R.color.parchemin_text
                    )

                    TrophyState.SILVER -> ContextCompat.getColorStateList(ctx, R.color.grey)
                    TrophyState.GOLD -> null
                    TrophyState.PLATINE -> ContextCompat.getColorStateList(
                        ctx,
                        R.color.colorPrimary
                    )
                }

                binding.tvTrophyItemTitle.text = ctx.getString(trophy.title)
                binding.tvTrophyItemTitle.setTextColor(
                    ContextCompat.getColor(ctx, R.color.colorPrimaryDark)
                )
                binding.tvTrophyItemMessage.text = ctx.getString(trophy.message)
                binding.tvTrophyItemMessage.setTextColor(
                    ContextCompat.getColor(ctx, R.color.text)
                )
            } else {
                binding.ivTrophyItemIcon.setImageResource(R.drawable.ic_lock)
                binding.ivTrophyItemIcon.imageTintList =
                    ContextCompat.getColorStateList(ctx, R.color.grey)
                binding.tvTrophyItemTitle.text = if (trophy.isHidden) {
                    ctx.getString(R.string.trophy_locked)
                } else {
                    ctx.getString(trophy.title)
                }
                binding.tvTrophyItemTitle.setTextColor(
                    ContextCompat.getColor(ctx, R.color.grey)
                )
                binding.tvTrophyItemMessage.text = ctx.getString(R.string.trophy_locked)
                binding.tvTrophyItemMessage.setTextColor(
                    ContextCompat.getColor(ctx, R.color.grey)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrophyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (trophy, unlocked) = items[position]
        holder.bind(trophy, unlocked)
    }

    override fun getItemCount() = items.size
}
