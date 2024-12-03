package com.example.alertifyapp.model

object NotificationManagerSingleton {
    // Make sure notificationsList is mutable and can be accessed and updated from any part of the app.
    val notificationsList: MutableList<NotificationContent> = mutableListOf()
}
