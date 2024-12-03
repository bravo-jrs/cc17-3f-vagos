package com.example.alertifyapp.model

// Define the NotificationContent data class here
data class NotificationContent(
    val title: String,
    val text: String,
    val timeStarted: String,
    val timeRestored: String,
    val purpose: String,
    val areasAffected: String,
    val imageResId: Int
)
