package com.oluwafemi.findadev.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.dev_details)
            setDisplayHomeAsUpEnabled(true)
        }
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        val devDetails = DetailsFragmentArgs.fromBundle(requireArguments()).selectedProperty

        binding.devName.text = devDetails.fullName
        binding.jobType.text = devDetails.toString()
        binding.devStack.text = devDetails.devStack

        binding.viewPortfolio.setOnClickListener {
            //Visit Portfolio Link

            val reformatedUrl = if(devDetails.devPortfolio.startsWith("https://")) devDetails.devPortfolio else "https://${devDetails.devPortfolio}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(reformatedUrl)
            startActivity(intent)
        }

        binding.connectWithDev.setOnClickListener {
            //Open email app
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data = Uri.parse("mailto:${devDetails.devEmail}")
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            }
            if (activity?.packageManager?.let { it1 -> intent.resolveActivity(it1) } != null) {
                startActivity(Intent.createChooser(intent, "Connect with this Developer"))
            }
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

}