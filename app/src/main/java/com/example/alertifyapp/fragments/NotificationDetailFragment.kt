package com.example.alertifyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.alertifyapp.R

class NotificationDetailFragment : Fragment(R.layout.fragment_notification_detail) {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var timeStartedTextView: TextView
    private lateinit var timeRestoredTextView: TextView
    private lateinit var purposeTextView: TextView
    private lateinit var affectedAreasTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification_detail, container, false)

        // Initialize views
        titleTextView = view.findViewById(R.id.text_title)
        descriptionTextView = view.findViewById(R.id.text_description)
        timeStartedTextView = view.findViewById(R.id.text_time_started)
        timeRestoredTextView = view.findViewById(R.id.text_time_restored)
        purposeTextView = view.findViewById(R.id.text_purpose)
        affectedAreasTextView = view.findViewById(R.id.text_areas_affected)
        imageView = view.findViewById(R.id.image_notification)

        // Extract data from the arguments
        arguments?.let {
            val notificationTitle = it.getString("notification_title")
            val notificationText = it.getString("notification_text")
            val notificationTimeStarted = it.getString("notification_time_started")
            val notificationTimeRestored = it.getString("notification_time_restored")
            val notificationPurpose = it.getString("notification_purpose")
            val notificationAreasAffected = it.getString("notification_areas_affected")
            val imageResId = it.getInt("notification_image_res_id", -1) // Image resource ID

            // Set the data to the UI elements
            titleTextView.text = notificationTitle
            descriptionTextView.text = notificationText
            timeStartedTextView.text = "Time Started: $notificationTimeStarted"
            timeRestoredTextView.text = "Time Restored: $notificationTimeRestored"
            purposeTextView.text = "Purpose: $notificationPurpose"
            affectedAreasTextView.text = "Affected Areas: $notificationAreasAffected"

            // If a valid image resource ID was passed, load it into the ImageView
            if (imageResId != -1) {
                imageView.setImageResource(imageResId)
            }
        }

        return view
    }
}
