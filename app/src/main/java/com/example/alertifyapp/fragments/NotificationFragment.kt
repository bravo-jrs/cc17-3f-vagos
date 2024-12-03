package com.example.alertifyapp.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.MainActivity
import com.example.alertifyapp.R
import com.example.alertifyapp.adapter.NotificationAdapter
import com.example.alertifyapp.model.NotificationContent
import com.example.alertifyapp.model.NotificationViewModel

const val CHANNEL_ID = "channelId"

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val notificationViewModel: NotificationViewModel by activityViewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("NotificationFragment", "Permission granted")
            sendNotificationsOnce() // Send notifications after permission is granted
        } else {
            Log.d("NotificationFragment", "Permission denied")
            Toast.makeText(requireContext(), "Permission denied to post notifications.", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var buttonUnscheduled: Button
    private lateinit var buttonScheduled: Button

    private var lastNotificationTime: Long = 0
    private val notificationInterval: Long = 5000 // 5 seconds

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recycler_view_notifications)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        notificationAdapter = NotificationAdapter(notificationViewModel.unscheduledNotifications) { notification: NotificationContent ->
            // Handle the click event here and pass notification details to the next fragment
            val bundle = Bundle().apply {
                putString("notification_title", notification.title)
                putString("notification_text", notification.text)
                putString("notification_time_started", notification.timeStarted)
                putString("notification_time_restored", notification.timeRestored)
                putString("notification_purpose", notification.purpose)
                putString("notification_areas_affected", notification.areasAffected)
                putInt("notification_image_res_id", notification.imageResId) // Use resource ID for image
            }

            val detailFragment = NotificationDetailFragment().apply {
                arguments = bundle
            }

            // Replace the fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack("NotificationDetailFragment") // Add a name to the back stack entry
                .commit() // Use commit() after the transaction
        }

        recyclerView.adapter = notificationAdapter

        // Set up buttons to switch between scheduled and unscheduled notifications
        buttonUnscheduled = view.findViewById(R.id.button_unscheduled)
        buttonScheduled = view.findViewById(R.id.button_scheduled)

        buttonUnscheduled.setOnClickListener {
            // Show unscheduled notifications
            notificationAdapter.updateNotifications(notificationViewModel.unscheduledNotifications)
        }

        buttonScheduled.setOnClickListener {
            // Show scheduled notifications
            notificationAdapter.updateNotifications(notificationViewModel.scheduledNotifications)
        }

        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {
                sendNotificationsOnce()
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            sendNotificationsOnce()
        }
    }

    private fun sendNotificationsOnce() {
        if (notificationViewModel.notificationsList.isNotEmpty()) return  // Avoid adding new notifications if already present

        createNotificationChannel()

        val notificationsContent = listOf(
            NotificationContent(
                title = "Unscheduled Power Interruption",
                text = "DATE: Saturday, November 30, 2024",
                timeStarted = "02:55 PM",
                timeRestored = "08:26 PM",
                purpose = "Facilitate load splitting of a communal Transformer located at City Camp.",
                areasAffected = "Baguio City: Parts of Lower Lourdes Subdivision, Lower Rock Quarry, City Camp.",
                imageResId = R.drawable.power_restored // Replace with your actual drawable resource
            ),
            NotificationContent(
                title = "Scheduled Power Interruption",
                text = "DATE: Sunday, December 1, 2024",
                timeStarted = "09:00 AM",
                timeRestored = "04:00 PM",
                purpose = "Maintenance of underground cables.",
                areasAffected = "Parts of Upper Rock Quarry.",
                imageResId = R.drawable.power_restored // Replace with your actual drawable resource
            ),
            NotificationContent(
                title = "Unscheduled Power Interruption",
                text = "DATE: Monday, December 2, 2024",
                timeStarted = "10:00 AM",
                timeRestored = "12:00 PM",
                purpose = "Routine inspection of power lines.",
                areasAffected = "Areas near Burnham Park.",
                imageResId = R.drawable.power_restored // Replace with your actual drawable resource
            ),
            NotificationContent(
                title = "Unscheduled Power Interruption",
                text = "DATE: Tuesday, December 3, 2024",
                timeStarted = "11:00 AM",
                timeRestored = "03:00 PM",
                purpose = "Upgrading of substation equipment.",
                areasAffected = "Parts of South Drive.",
                imageResId = R.drawable.power_restored // Replace with your actual drawable resource
            )
        )

        // Add notifications to the ViewModel (persistent across fragment recreations)
        for (i in notificationsContent.indices) {
            val content = notificationsContent[i]

            notificationViewModel.notificationsList.add(content)

            // Separate notifications based on type
            if (content.title.contains("Unscheduled")) {
                notificationViewModel.unscheduledNotifications.add(content)
            } else {
                notificationViewModel.scheduledNotifications.add(content)
            }

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastNotificationTime >= notificationInterval) {
                lastNotificationTime = currentTime

                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    putExtra("open_notification_fragment", true)
                    putExtra("notification_title", content.title)
                    putExtra("notification_text", content.text)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent = PendingIntent.getActivity(
                    requireContext(),
                    i,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(content.title)
                    .setContentText(content.text)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED) {
                    with(NotificationManagerCompat.from(requireContext())) {
                        notify(i + 1, builder.build())
                    }
                } else {
                    Log.d("NotificationFragment", "Permission not granted, cannot send notification")
                }
            }
        }

        // Show unscheduled notifications initially
        notificationAdapter.updateNotifications(notificationViewModel.unscheduledNotifications)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Test Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for notifications"
        }
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}