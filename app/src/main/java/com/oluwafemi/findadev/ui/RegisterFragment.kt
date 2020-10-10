package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val techStacks = arrayListOf(
        "Front-end", "Back-end", "DevOps", "Mobile(Native)",
        "Mobile(Cross platform)", "UI/UX", "Data Science", "ML/DL"
    )
    private val jobType = arrayListOf("Full-Time", "Remote", "Contract")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.dev_reg)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)

        val stackAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, techStacks)
        stackAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.selectStack.adapter = stackAdapter

        val jobAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, jobType)
        jobAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.jobType.adapter = jobAdapter


        return binding.root
    }

}