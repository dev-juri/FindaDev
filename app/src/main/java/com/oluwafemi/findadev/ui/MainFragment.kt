package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.adapter.DevListAdapter
import com.oluwafemi.findadev.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.devListRecyclerView.adapter = DevListAdapter()

        return binding.root
    }
}