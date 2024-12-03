package com.example.alertifyapp.model

import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {
    // List to hold notifications
    val notificationsList = mutableListOf<NotificationContent>()
    val unscheduledNotifications = mutableListOf<NotificationContent>()
    val scheduledNotifications = mutableListOf<NotificationContent>()
}