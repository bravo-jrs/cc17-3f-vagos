package com.example.alertifyapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReportOutage : AppCompatActivity() {

    private lateinit var addressInput: EditText
    private lateinit var radioReports: RadioGroup
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_outage)

        addressInput = findViewById(R.id.enter_address)
        radioReports = findViewById(R.id.radio_reports)
        submitButton = findViewById(R.id.submit_button)

        submitButton.setOnClickListener {
            val address = addressInput.text.toString()
            val selectedId = radioReports.checkedRadioButtonId

            if (address.isEmpty() || selectedId == -1) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val reportType = selectedRadioButton.text.toString()

            val intent = Intent(this, UploadReports::class.java).apply {
                putExtra("address", address)
                putExtra("reportType", reportType)
            }
            startActivity(intent)
        }
    }
}
