package com.oluwafemi.findadev.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.databinding.FragmentRegisterBinding
import com.oluwafemi.findadev.model.Dev
import com.oluwafemi.findadev.viewmodels.RegisterFragmentViewModel
import com.oluwafemi.findadev.viewmodels.UploadStatus

class RegisterFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(RegisterFragmentViewModel::class.java)
    }
    private lateinit var binding: FragmentRegisterBinding
    private val techStacks = arrayListOf(
        "Front-end", "Back-end", "DevOps", "Mobile(Native)", "Full Stack",
        "Mobile(Cross platform)", "UI/UX", "Data Science", "ML/DL"
    )
    private val jobType = arrayListOf("All", "Full-Time", "Remote", "Contract")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        val fullName = binding.fullName.editText
        val emailAddress = binding.emailAddress.editText
        val portfolioUrl = binding.linkToPortfolio.editText

        val stackAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, techStacks)
        stackAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.selectStack.adapter = stackAdapter

        val jobAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, jobType)
        jobAdapter.setDropDownViewResource(R.layout.spinner_list)
        binding.jobType.adapter = jobAdapter

        watchFields()
        binding.registerBtn.setOnClickListener {
            val fullNameText = fullName?.text?.trim().toString()
            val emailAddressText = emailAddress?.text?.trim().toString()
            val urlLink = portfolioUrl?.text?.trim().toString()
            val devStack = binding.selectStack.selectedItem.toString()
            val devInterest = binding.jobType.selectedItem.toString()

            val devDetails = Dev(fullNameText, emailAddressText, urlLink, devStack, devInterest)
            if (fullNameText.isEmpty() || emailAddressText.isEmpty() || urlLink.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(emailAddressText).matches() ||
                !Patterns.WEB_URL.matcher(urlLink).matches()
            ) {
                Snackbar.make(
                    binding.registerBtn,
                    getString(R.string.validation_msg),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                viewModel.checkUser(devDetails, emailAddressText)
                viewModel.status.observe(viewLifecycleOwner, Observer {
                    when(it){
                        UploadStatus.SUCCESS -> {
                            Toast.makeText(context, "Successfully Created User", Toast.LENGTH_LONG).show()
                            this.findNavController().navigate(R.id.action_registerFragment_to_nav_graph_home)
                        }
                        UploadStatus.USER_EXISTS -> Toast.makeText(context, "User with email $emailAddressText already exists", Toast.LENGTH_LONG).show()
                        else -> Toast.makeText(context, "Failure: Please check your network and try again", Toast.LENGTH_LONG).show()
                    }
                })
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