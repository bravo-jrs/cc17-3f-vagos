package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alertifyapp.databinding.ActivityAccessLocationBinding

class AccessLocation : AppCompatActivity() {

    private lateinit var binding: ActivityAccessLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAccessLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val commonClickListener = {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragmentToLoad", "home")
            startActivity(intent)
            finish()
        }

        binding.enable.setOnClickListener { commonClickListener() }
        binding.onlythistime.setOnClickListener { commonClickListener() }
        binding.notnow.setOnClickListener { commonClickListener() }
    }
}