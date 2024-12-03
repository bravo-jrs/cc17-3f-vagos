package com.example.alertifyapp.model

import androidx.lifecycle.ViewModel

class ReportViewModel : ViewModel() {

    // Method to handle the submission of the report
    fun submitReport(outageType: String, damageDescription: String, address: String) {
        // Here you can handle the logic for submitting the report,
        // such as saving it to a database or sending it to a server.

        // For demonstration, we're just printing the values.
        println("Report Submitted:")
        println("Outage Type: $outageType")
        println("Damage Description: $damageDescription")
        println("Address: $address")

        // You can add further logic such as validation, network requests, or saving to a database.
    }
}