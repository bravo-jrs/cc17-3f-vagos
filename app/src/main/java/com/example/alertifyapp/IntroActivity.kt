package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alertifyapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() { // Define IntroActivity class inheriting from AppCompatActivity

    private lateinit var binding: ActivityIntroBinding // Declare a variable for View Binding

    override fun onCreate(savedInstanceState: Bundle?) { // Override onCreate method
        super.onCreate(savedInstanceState) // Call the superclass's onCreate method
        //enableEdgeToEdge()

        binding = ActivityIntroBinding.inflate(layoutInflater) // Inflate the layout using View Binding
        setContentView(binding.root) // Set the content view to the root of the binding

        // Set a click listener on the getStarted button to go to LoginScreenActivity
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, Loginscreen::class.java)) // Start LoginScreenActivity
            finish() // Close IntroActivity
        }
    }
}