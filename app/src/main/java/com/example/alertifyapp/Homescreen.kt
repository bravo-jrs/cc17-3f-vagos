package com.example.alertifyapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.adapter.HomeScreenAdapter
import com.example.alertifyapp.fragments.HomeFragment
import com.example.alertifyapp.fragments.NotificationFragment
import com.example.alertifyapp.fragments.SettingFragment
import com.example.alertifyapp.model.HomeScreen
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class Homescreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homescreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvhomescreen: RecyclerView = findViewById(R.id.rvhomescreen)
        rvhomescreen.layoutManager = LinearLayoutManager(this)

        val homeScreen1 = HomeScreen(R.drawable.red_call_plus_removebg_preview,
            "Emergency Hotline",
            "911",
             ) {/*Button*/ }

        val homeScreen2 = HomeScreen(R.drawable.cdrrmo_logo_removebg_preview,
            "Baguio City Risk Reduction\nManagement Office (CDRRMO)",
            "442-1900\n442=1901\n442-1905\n09276280498 (Globe)\n09996784335 (Smart)",
             ) {/*Button*/ }

        val homeScreen3 = HomeScreen(R.drawable.baguio_s_finest_removebg_preview,
            "Baguio City Police Office",
            "166\n661-1471\n09175758993 (Globe/TM)\n09985987739 (Smart/TNT)",
             ) {/*Button*/}

        val homeScreen4 = HomeScreen(R.drawable.beneco_icon_removebg_preview,
            "Benguet Electric Cooperative\n (BENECO)",
            "637-4400\n09175921698 (Globe/TM)\n09088657202 (Smart/TNT)",
             ) {/*Button*/ }

        val homeScreen5 = HomeScreen(R.drawable.emsbaguiocity_icon_removebg_preview,
            "Baguio City Emergency Medical\nServices (BCEMS)",
            "442-1911\n442-1901\n09055551911 (Globe/TM)\n09213208052 (Smart/TNT)",
             ) {/*Button*/ }

        val homeScreen6 = HomeScreen(R.drawable.baguio_sbfp_icon_removebg,
            "Baguio City Fire Station",
            "422-2222\n443-7089\n09124096114 (Smart/TNT)",
             ) {/*Button*/ }

        val homeScreen7 = HomeScreen(R.drawable.bawadi_icon_removebg_preview,
            "Baguio Water District (BWD)",
            "442-3218\n442-5539\n09176794929 (Globe/TM)\n09088651504 (Smart/TNT)",
             ) {/*Button*/ }

        val arrList = listOf(homeScreen1, homeScreen2, homeScreen3, homeScreen4, homeScreen5,homeScreen6,homeScreen7)

        rvhomescreen.adapter = HomeScreenAdapter(arrList)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home",R.drawable.homeicon)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Notification",R.drawable.bellicon)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Contact",R.drawable.contactus)
        )

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> {
                    replaceFragment(HomeFragment())
                }
                2 -> {
                    replaceFragment(NotificationFragment())
                }
                3-> {
                    replaceFragment(SettingFragment())
                }
            }
        }

        replaceFragment(HomeFragment())
        bottomNavigation.show(2)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}