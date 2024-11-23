package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alertifyapp.databinding.ActivityLoginscreenBinding

class Loginscreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginscreenBinding.inflate(layoutInflater) // Inflate the layout using View Binding
        setContentView(binding.root) // Set the content view to the root of the binding

        // Set a click listener on the button to go to JoinTodayActivity
        binding.signup.setOnClickListener {
            startActivity(Intent(this, JoinToday::class.java)) // Start JoinTodayActivity
        }

        // Set a click listener on the button to go to AccessLocation
        binding.signinow.setOnClickListener {
            val intent = Intent(this, AccessLocation::class.java) // Create an intent for AccessLocation
            startActivity(intent) // Start AccessLocation activity
            finish() // Close Loginscreen activity
        }
    }
}