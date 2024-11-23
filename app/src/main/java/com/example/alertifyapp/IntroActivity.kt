package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alertifyapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the Intro Activity layout
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button to finish intro and proceed
        binding.startButton.setOnClickListener {
            markIntroCompleted()
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close the IntroActivity
        }
    }

    private fun markIntroCompleted() {
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
    }
}
