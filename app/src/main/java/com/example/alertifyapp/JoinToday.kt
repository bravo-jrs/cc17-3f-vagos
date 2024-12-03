package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alertifyapp.database.User
import com.example.alertifyapp.database.UserDatabase
import com.example.alertifyapp.databinding.ActivityJoinTodayBinding
import kotlinx.coroutines.launch

class JoinToday : AppCompatActivity() {
    private lateinit var binding: ActivityJoinTodayBinding
    private lateinit var userDatabase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinTodayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = UserDatabase.getDatabase(this)

        binding.createAccount.setOnClickListener {
            // Retrieve input fields
            val name = binding.nameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            // Validate inputs
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    // Proceed with account creation
                    val user = User(name = name, email = email, password = password)
                    lifecycleScope.launch {
                        try {
                            // Insert user into the database
                            userDatabase.userDao().insert(user)
                            Toast.makeText(this@JoinToday, "Account Created Successfully!", Toast.LENGTH_SHORT).show()

                            // Navigate to the login screen
                            startActivity(Intent(this@JoinToday, Loginscreen::class.java))
                            finish()
                        } catch (e: Exception) {
                            Toast.makeText(this@JoinToday, "Error creating account: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Password validation failed
                    Toast.makeText(this@JoinToday, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If any field is empty, show this message
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
