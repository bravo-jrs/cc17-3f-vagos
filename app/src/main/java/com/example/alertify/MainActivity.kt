package com.example.alertify

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertify.adapter.HomePageAdapter
import com.example.alertify.model.HomePage
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val homePage: RecyclerView = findViewById(R.id.homePage)
        homePage.layoutManager = LinearLayoutManager(this)

        val homePage1 = HomePage(R.drawable.red_call_plus_removebg_preview,
            "Emergency Hotline",
            "911" )
            {}
        val homePage2 = HomePage(R.drawable.cdrrmo_logo_removebg_preview,
            "Baguio City Risk Reduction\n Management Office (CDRRMO)",
            "442-1900\n442-1901\n442-1905\n09276280498 (Globe)\n09996784335 (Smart)")
            {}
        val homePage3 = HomePage(R.drawable.baguio_s_finest_removebg_preview,
            "Baguio City Police Office",
            " 166\n661-1471\n09175758993 (Globe/TM)\n09985987739 (Smart/TNT)")
            {}
        val homePage4 = HomePage(R.drawable.beneco_icon_removebg_preview,
            "Benguet Electric Cooperative\n(BENECO)",
            " 637-4400\n09175921698(Globe/TM)\n09175758993 (Globe/TM)\n09088657202 (Smart/TNT)")
            {}
        val homePage5 = HomePage(R.drawable.emsbaguiocity_icon_removebg_preview,
            "Baguio City Emergency Medical\nServices (BCEMS)",
            " 442-1911\n442-1901\n09055551911 (Globe/TM)\n09213208052 (Smart/TNT)")
            {}
        val homePage6 = HomePage(R.drawable.baguio_sbfp_icon_removebg,
            "Baguio City Fire Station",
            " 422-2222\n443-7089\n09124096114 (Smart/TNT)")
            {}
        val homePage7 = HomePage(R.drawable.bawadi_icon_removebg_preview,
            "Baguio Water District (BWD)",
            " 442-3218\n442-5539\n09176794929 (Globe/TM)\n09088651504 (Smart/TNT)")
            {}

        val arrList = Arrays.asList(homePage1, homePage2, homePage3, homePage4, homePage5, homePage6, homePage7)

        homePage.adapter = HomePageAdapter(arrList)
    }
}