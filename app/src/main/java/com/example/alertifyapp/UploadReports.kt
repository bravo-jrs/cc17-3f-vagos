package com.example.alertifyapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UploadReports : AppCompatActivity() {

    private lateinit var addressText: TextView
    private lateinit var reportTypeText: TextView
    private lateinit var uploadImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_activity)

        addressText = findViewById(R.id.addressText)
        reportTypeText = findViewById(R.id.reportTypeText)
        uploadImage = findViewById(R.id.submitted_check)

        val address = intent.getStringExtra("address")
        val reportType = intent.getStringExtra("reportType")

        addressText.text = "Address: $address"
        reportTypeText.text = "Report Type: $reportType"

        uploadImage.setOnClickListener {
            // Handle image upload logic here
            Toast.makeText(this, "Image Upload clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
