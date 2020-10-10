package com.oluwafemi.findadev.util

import androidx.databinding.Bindable
import androidx.recyclerview.widget.RecyclerView
import com.oluwafemi.findadev.adapter.DevListAdapter
import com.oluwafemi.findadev.model.Dev

@Bindable("devList")
fun bindDev(recyclerView: RecyclerView, data : List<Dev>?) {
    val adapter = recyclerView.adapter as DevListAdapter
    adapter.submitList(data)
}