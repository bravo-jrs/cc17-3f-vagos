package com.example.alertifyapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.alertifyapp.databinding.ActivityMainBinding
import com.example.alertifyapp.fragments.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if this is the first launch
        val isFirstLaunch = checkFirstLaunch()
        if (isFirstLaunch) {
            // Navigate to Intro Screen
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Prevent returning to this activity
            return
        }

        // If location permissions are not granted, navigate to location access screen
        if (!isLocationPermissionGranted()) {
            startActivity(Intent(this, AccessLocation::class.java))
            finish()
            return
        }

        // Inflate main layout
        binding = .inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the toolbar
        setSupportActionBar(binding.toolbar)

        // Setup drawer toggle
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup Navigation Drawer
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        // Initialize fragment manager
        fragmentManager = supportFragmentManager

        // Set Bottom Navigation behavior
        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_notification -> openFragment(NotificationFragment())
                R.id.bottom_report -> openFragment(ReportFragment())
            }
            true
        }

        // Start with HomeFragment
        openFragment(HomeFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_contact_us -> openFragment(ContactFragment())
            R.id.nav_about_us -> openFragment(AboutFragment())
            R.id.nav_faqs -> openFragment(FaqsFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.item_view, fragment)
        fragmentTransaction.commit()
    }

    private fun checkFirstLaunch(): Boolean {
        // Logic to check if this is the first launch
        val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)
        if (isFirstLaunch) {
            sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
        }
        return isFirstLaunch
    }

    private fun isLocationPermissionGranted(): Boolean {
        // Add logic to check location permissions
        // This can use ContextCompat.checkSelfPermission()
        return true // Return true for now as a placeholder
    }
}
