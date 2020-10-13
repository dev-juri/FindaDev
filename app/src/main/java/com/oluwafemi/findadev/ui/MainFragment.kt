package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.adapter.DevListAdapter
import com.oluwafemi.findadev.databinding.FragmentMainBinding
import com.oluwafemi.findadev.viewmodels.MainFragmentViewModel


class MainFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.meet_dev)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.devListRecyclerView.adapter = DevListAdapter()

        return binding.root
    }
}