package com.example.alertifyapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alertifyapp.database.UserDatabase
import com.example.alertifyapp.databinding.ActivityLoginscreenBinding
import kotlinx.coroutines.launch

class Loginscreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginscreenBinding
    private lateinit var userDatabase: UserDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = UserDatabase.getDatabase(this)
        sharedPreferences = getSharedPreferences("User Prefs", MODE_PRIVATE)

        // Sign in button click listener
        binding.signinow.setOnClickListener {
            val email = binding.emailInputLogin.text.toString().trim()
            val password = binding.passwordInputLogin.text.toString().trim()

            // Check if email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = userDatabase.userDao().login(email, password)
                    if (user != null) {
                        // Successful login
                        // Store email in SharedPreferences
                        with(sharedPreferences.edit()) {
                            putString("user_email", email)
                            apply()
                        }

                        // Navigate to MainActivity
                        val intent = Intent(this@Loginscreen, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close the login screen
                    } else {
                        // Show error if login fails
                        Toast.makeText(this@Loginscreen, "Invalid email or password.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Show error if any of the fields are empty
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        // Sign up button listener (to navigate to JoinToday screen for registration)
        binding.signup.setOnClickListener {
            val intent = Intent(this, JoinToday::class.java) // Navigate to registration screen
            startActivity(intent)
        }
    }
}