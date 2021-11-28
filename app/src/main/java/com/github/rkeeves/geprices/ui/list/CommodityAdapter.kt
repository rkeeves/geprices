package com.github.rkeeves.geprices.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.rkeeves.geprices.data.local.Commodity
import com.github.rkeeves.geprices.databinding.ListItemCommodityBinding

// dude wtf
class CommodityAdapter(private val clickListener: CommodityClickListener) : ListAdapter<Commodity,
        CommodityAdapter.ViewHolder>(CommodityDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemCommodityBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Commodity, clickListener: CommodityClickListener) {
            binding.commodity = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCommodityBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CommodityDiffCallback : DiffUtil.ItemCallback<Commodity>() {

    override fun areItemsTheSame(oldItem: Commodity, newItem: Commodity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Commodity, newItem: Commodity): Boolean {
        return oldItem == newItem
    }
}
