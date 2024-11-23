package com.example.alertifyapp

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
        setContentView(R.layout.activity_main)

        // Inflate main layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the toolbar
        setSupportActionBar(binding.toolbar)

        // Setup drawer toggle for the navigation drawer
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle) // Add toggle listener to the drawer layout
        toggle.syncState() // Sync the toggle state with the drawer layout

        // Setup Navigation Drawer item selection listener
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        // Initialize fragment manager
        fragmentManager = supportFragmentManager

        // Set Bottom Navigation behavior
        binding.bottomNavigation.background = null // Remove default background
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment()) // Open HomeFragment
                R.id.bottom_notification -> openFragment(NotificationFragment()) // Open NotificationFragment
                R.id.bottom_report -> openFragment(ReportFragment()) // Open ReportFragment
            }
            true // Indicate that the item selection was handled
        }

        // Start with HomeFragment by default
        openFragment(HomeFragment())
    }

    // Handle navigation item selection from the Navigation Drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_contact_us -> openFragment(ContactusFragment()) // Open ContactFragment
            R.id.nav_about_us -> openFragment(AboutusFragment()) // Open AboutFragment
            R.id.nav_faqs -> openFragment(FaqsFragment()) // Open FaqsFragment
            else -> return false // Handle unexpected selections
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer after selection
        return true // Indicate that the selection was handled
    }

    // Handle back button press using the new OnBackPressedDispatcher

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer if it's open
        } else {
            // Call the default backPressed behavior after handling drawer
            super.onBackPressed()
        }
    }


    // Method to open a specific fragment
    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction() // Begin a fragment transaction
        fragmentTransaction.replace(R.id.fragment_container, fragment) // Replace the current fragment with the new one
        fragmentTransaction.commit() // Commit the transaction
    }
}
