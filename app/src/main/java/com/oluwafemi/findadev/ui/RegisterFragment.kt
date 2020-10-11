package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
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
        val fullName = binding.fullName.editText?.text?.trim().toString()
        val emailAddress = binding.emailAddress.editText?.text?.trim().toString()
        val portfolioUrl = binding.linkToPortfolio.editText?.text?.trim().toString()
        val devStack = binding.selectStack.selectedItem
        val devInterest = binding.jobType.selectedItem

        val stackAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, techStacks)
        stackAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.selectStack.adapter = stackAdapter

        val jobAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, jobType)
        jobAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.jobType.adapter = jobAdapter

        watchFields()
        binding.registerBtn.setOnClickListener {
            if (fullName.isEmpty() || emailAddress.isEmpty() || portfolioUrl.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(binding.emailAddress.editText?.text.toString())
                    .matches() ||
                Patterns.WEB_URL.matcher(binding.linkToPortfolio.editText?.text.toString())
                    .matches()
            ) {
                Snackbar.make(binding.registerBtn, getString(R.string.validation_msg), Snackbar.LENGTH_LONG).show()
            } else {
                Log.i("REG_INFO", "Dear $fullName with email $emailAddress and url $portfolioUrl")
            }
        }

        return binding.root
    }

    private fun watchFields() {
        binding.fullName.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        binding.fullName.error = ""
                    } else {
                        binding.fullName.error = "Required Field"
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        binding.fullName.error = ""
                    }
                }
            }
        })
        binding.emailAddress.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailAddress.editText?.text.toString())
                                .matches()
                        ) {
                            binding.emailAddress.error = "Invalid email format"
                        }
                    } else {
                        binding.emailAddress.error = "Required Field"
                    }
                } else {
                    binding.emailAddress.error = ""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        binding.emailAddress.error = ""
                    }
                }
            }
        })
        binding.linkToPortfolio.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        if (!Patterns.WEB_URL.matcher(binding.linkToPortfolio.editText?.text.toString())
                                .matches()
                        ) {
                            binding.linkToPortfolio.error = "Invalid url format"
                        }
                    } else {
                        binding.linkToPortfolio.error = "Required Field"
                    }
                } else {
                    binding.linkToPortfolio.error = ""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        binding.linkToPortfolio.error = ""
                    }
                }
            }
        })
    }

}