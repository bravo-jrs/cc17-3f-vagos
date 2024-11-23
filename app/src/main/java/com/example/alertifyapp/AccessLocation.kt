package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AccessLocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_access_location)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val fourthButton = findViewById<Button>(R.id.enable)
//        fourthButton.setOnClickListener {
//            val intent4 = Intent(this, )
//        }
//        val fifthButton = findViewById<Button>(R.id.onlythistime)
//        fifthButton.setOnClickListener {
//            val intent5 = Intent (this, )
//        }
        val sixthButton = findViewById<Button>(R.id.notnow)
        sixthButton.setOnClickListener {
            val intent6 = Intent (this, Homescreen::class.java)
            startActivity(intent6)
        }








        }

    }
