package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alertifyapp.databinding.ActivityJoinTodayBinding

class JoinToday : AppCompatActivity() {
    private lateinit var binding: ActivityJoinTodayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinTodayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAccount.setOnClickListener {
            val intent = Intent(this, Loginscreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}