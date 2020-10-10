package com.oluwafemi.findadev.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.databinding.DevListBinding
import com.oluwafemi.findadev.model.Dev

class DevListAdapter : ListAdapter<Dev, DevListAdapter.DevViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevViewHolder {
        val withBinding: DevListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevViewHolder.LAYOUT,
            parent,
            false
        )
        return DevViewHolder(withBinding)
    }

    override fun onBindViewHolder(holder: DevViewHolder, position: Int) {
        val eachDev = getItem(position)
        holder.bind(eachDev)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Dev>() {
        override fun areItemsTheSame(oldItem: Dev, newItem: Dev): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dev, newItem: Dev): Boolean {
            return oldItem.fullName == newItem.fullName
        }

    }

    class DevViewHolder(private val binding: DevListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dev: Dev) {
            binding.devOverview = dev
            binding.executePendingBindings()
        }

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.dev_list
        }
    }

}