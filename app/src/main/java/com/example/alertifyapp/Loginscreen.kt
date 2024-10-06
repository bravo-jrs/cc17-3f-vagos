package com.example.alertifyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Loginscreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val secondButton = findViewById<Button>(R.id.signin)
        secondButton.setOnClickListener {
            val intent2 = Intent (this, Access_Location::class.java)
            startActivity(intent2)

//        val forgot_password = findViewById<TextView>(R.id.forgot_password)
//        forgot_password.setOnClickListener {
//            val inTent = Intent(this, )
//            startActivity(inTent)

        }

    }
}