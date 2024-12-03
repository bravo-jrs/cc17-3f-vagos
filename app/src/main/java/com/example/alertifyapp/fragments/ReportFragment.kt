package com.example.alertifyapp.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.alertifyapp.databinding.FragmentReportBinding
import com.example.alertifyapp.R
import com.example.alertifyapp.model.ReportViewModel


class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding // Correctly typed binding
    private val viewModel: ReportViewModel by viewModels() // Using viewModels() for ViewModelProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)

        // Set up the submit button's click listener
        binding.btnSubmit.setOnClickListener {
            // Get the selected outage type from the radio buttons
            val outageType = when (binding.rgOutageType.checkedRadioButtonId) {
                R.id.rb_house_outage -> "House Outage"
                R.id.rb_street_outage -> "Street Outage"
                R.id.rb_unstable_power -> "Unstable Power"
                R.id.rb_streetlight_issue -> "Streetlight Issue"
                else -> ""
            }

            // Get user inputs for damage description and address
            val damageDescription = binding.etDamageDescription.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()

            // Check if any field is empty before proceeding
            if (outageType.isNotEmpty() && damageDescription.isNotEmpty() && address.isNotEmpty()) {
                // Call ViewModel to handle report submission
                viewModel.submitReport(outageType, damageDescription, address)

                // Show a toast message on successful submission
                Toast.makeText(requireContext(), "Report Submitted Successfully!", Toast.LENGTH_SHORT).show()

                // Optionally, clear the input fields after submission
                binding.etDamageDescription.text.clear()
                binding.etAddress.text.clear()
                binding.rgOutageType.clearCheck()
            } else {
                // Show a toast message if any field is empty
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        // Return the root view
        return binding.root
    }
}