package com.example.alertifyapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.alertifyapp.database.User
import com.example.alertifyapp.database.UserDatabase
import com.example.alertifyapp.databinding.FragmentProfileBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import android.util.Log
import android.view.inputmethod.InputMethodManager

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userDatabase: UserDatabase
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userDatabase = UserDatabase.getDatabase(requireContext())

        // Load user data from the database
        loadUser()

        // Set click listeners to enable editing
        binding.nameInput.setOnClickListener {
            enableEditing(binding.nameInput)
        }
        binding.emailInput.setOnClickListener {
            enableEditing(binding.emailInput)
        }
        binding.passwordInput.setOnClickListener {
            enableEditing(binding.passwordInput)
        }
        binding.confirmPasswordInput.setOnClickListener {
            enableEditing(binding.confirmPasswordInput)
        }

        // Set the save button's click listener to save updated data
        binding.saveButton.setOnClickListener {
            saveUser()
        }

        return binding.root
    }

    // Function to load user data from the database and display it in the text fields
    private fun loadUser() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("user_email", null)

        if (email != null) {
            Log.d("ProfileFragment", "Email retrieved from SharedPreferences: $email")
            lifecycleScope.launch {
                try {
                    // Attempt to fetch the user data from the database
                    currentUser = userDatabase.userDao().getUserByEmail(email)

                    currentUser?.let {
                        // Populate the text fields with the user's data
                        binding.nameInput.setText(it.name)
                        binding.emailInput.setText(it.email)
                        binding.passwordInput.setText("")  // Do not show the password
                        binding.confirmPasswordInput.setText("")
                    } ?: run {
                        Toast.makeText(requireContext(), "User not found in the database.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("ProfileFragment", "Error loading user data: ${e.message}")
                    Toast.makeText(requireContext(), "Error loading user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.e("ProfileFragment", "No email found in SharedPreferences")
            Toast.makeText(requireContext(), "Email not found in preferences.", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to enable editing for a particular TextInputEditText
    private fun enableEditing(editText: TextInputEditText) {
        // Enable the field for editing
        editText.isEnabled = true
        // Request focus so the keyboard will show
        editText.requestFocus()

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    // Function to save the updated user data
    private fun saveUser() {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

        // Log the data being saved for debugging
        Log.d("ProfileFragment", "Saving user: $name, $email, $password, $confirmPassword")

        if (name.isNotEmpty() && email.isNotEmpty()) {
            if (password.isNotEmpty() || confirmPassword.isNotEmpty()) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        currentUser?.let {
                            lifecycleScope.launch {
                                val updatedUser = it.copy(name = name, email = email, password = password)
                                userDatabase.userDao().update(updatedUser)
                                Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                                disableEditing()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If password is empty, just update the name and email
                currentUser?.let {
                    lifecycleScope.launch {
                        val updatedUser = it.copy(name = name, email = email)
                        userDatabase.userDao().update(updatedUser)
                        Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                        disableEditing()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to disable editing after saving data
    private fun disableEditing() {
        binding.nameInput.isEnabled = false
        binding.emailInput.isEnabled = false
        binding.passwordInput.isEnabled = false
        binding.confirmPasswordInput.isEnabled = false
    }
}
