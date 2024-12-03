package com.example.alertifyapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alertifyapp.database.UserDatabase
import com.example.alertifyapp.databinding.ActivityMainBinding
import com.example.alertifyapp.model.PushNotificationContent
import com.example.alertifyapp.model.UserViewModel
import com.example.alertifyapp.model.UserViewModelFactory
import com.example.alertifyapp.fragments.AboutusFragment
import com.example.alertifyapp.fragments.ContactusFragment
import com.example.alertifyapp.fragments.FaqsFragment
import com.example.alertifyapp.fragments.HomeFragment
import com.example.alertifyapp.fragments.NotificationFragment
import com.example.alertifyapp.fragments.ProfileFragment
import com.example.alertifyapp.fragments.ReportFragment
import com.google.android.material.navigation.NavigationView

const val CHANNEL_ID = "channelId"
const val PERMISSION_REQUEST_CODE = 1001

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("User Prefs", MODE_PRIVATE)

        // Initialize the UserViewModel
        val userDao = UserDatabase.getDatabase(application).userDao()
        val factory = UserViewModelFactory(userDao)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        // Send notifications when app is opened
        sendMultipleNotifications()

        // Handle intent if notification fragment should be opened
        if (intent?.getBooleanExtra("open_notification_fragment", false) == true) {
            openFragment(NotificationFragment())
        } else {
            openFragment(HomeFragment())
        }

        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_notification -> openFragment(NotificationFragment())
                R.id.bottom_report -> openFragment(ReportFragment())
            }
            true
        }

        // Fetch user data and update the navigation header
        val userEmail = sharedPreferences.getString("user_email", null)
        userEmail?.let { email ->
            userViewModel.getUserByEmail(email) { user ->
                user?.let {
                    // Update the navigation header with user data
                    val navHeaderView = binding.navigationDrawer.getHeaderView(0)
                    navHeaderView.findViewById<TextView>(R.id.user_name).text = it.name
                    navHeaderView.findViewById<TextView>(R.id.user_email).text = it.email
                }
            }
        }
    }

    // Method to send notifications
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendMultipleNotifications() {
        // Create notification channel if required (for API 26+)
        createNotificationChannel()

        // Define different content for each notification
        val notificationsContent = listOf(
            PushNotificationContent("Notification 1", "This is the first notification"),
            PushNotificationContent("Notification 2", "This is the second notification"),
            PushNotificationContent("Notification 3", "This is the third notification"),
            PushNotificationContent("Notification 4", "This is the fourth notification")
        )

        // Loop through the notificationsContent and send each notification
        for (i in notificationsContent.indices) {
            val content = notificationsContent[i]

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("open_notification_fragment", true)  // Flag to show NotificationFragment
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(
                this,
                i,  // Use different request codes for different notifications
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Build the notification
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(content.title)
                .setContentText(content.text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            // Ensure permission is granted before posting the notification
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {
                // Send the notification with a unique ID (using i as the notification ID)
                with(NotificationManagerCompat.from(this)) {
                    notify(i + 1, builder.build()) // Use unique notification IDs (1, 2, 3, 4)
                }
            } else {
                Log.d("MainActivity", "Permission not granted, cannot send notification")
                requestNotificationPermission() // Request permission if not granted
            }
        }
    }

    // Request notification permission for Android 13+
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
        }
    }

    // Create notification channel for Android 8.0 (API 26) and above
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Test Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for notifications"
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Method to open a fragment
    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)  // Add the transaction to the back stack
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> openFragment(ProfileFragment())
            R.id.nav_contact_us -> openFragment(ContactusFragment())
            R.id.nav_about_us -> openFragment(AboutusFragment())
            R.id.nav_faqs -> openFragment(FaqsFragment())
            R.id.nav_logout -> {
                logout() // Call logout method
                return true
            }
            else -> return false
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        // Clear SharedPreferences
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }

        // Navigate to Login screen
        val intent = Intent(this, Loginscreen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish() // Close the main activity
    }

    // Handle the permission result for notification permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Notification permission granted")
            } else {
                Log.d("MainActivity", "Notification permission denied")
            }
        }
    }
}
