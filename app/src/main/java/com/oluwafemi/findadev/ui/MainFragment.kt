package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.adapter.DevListAdapter
import com.oluwafemi.findadev.databinding.FragmentMainBinding
import com.oluwafemi.findadev.viewmodels.MainFragmentViewModel


class MainFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val chipGroup = binding.sortByStack
        val inflator = LayoutInflater.from(chipGroup.context)

        val children = RegisterFragment.techStacks.map { stack ->
            val chip = inflator.inflate(R.layout.sort_stack, chipGroup, false) as Chip
            chip.text = stack
            chip.tag = stack
            chip.setOnCheckedChangeListener { button, isChecked ->
                //Check checked state
            }
            chip
        }
        for (chip in children) {
            chipGroup.addView(chip)
        }

        binding.devListRecyclerView.adapter = DevListAdapter(DevListAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.showDetail(it))
                viewModel.displayPropertyDetailsCompleted()
            }
        })


        return binding.root
    }
}