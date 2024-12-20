package com.example.alertifyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.R
import com.example.alertifyapp.adapter.HomeScreenAdapter
import com.example.alertifyapp.model.HomeScreen

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        val rvhomescreen: RecyclerView = view.findViewById(R.id.rvhomescreen)
        rvhomescreen.layoutManager = LinearLayoutManager(requireContext())

        // Create data for RecyclerView
        val arrList = listOf(
            HomeScreen(
                R.drawable.red_call_plus_removebg_preview,
                "Emergency Hotline",
                "911"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.cdrrmo_logo_removebg_preview,
                "Baguio City Risk Reduction\nManagement Office (CDRRMO)",
                "442-1900\n442-1901\n442-1905\n09276280498 (Globe)\n09996784335 (Smart)"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.baguio_s_finest_removebg_preview,
                "Baguio City Police Office",
                "166\n661-1471\n09175758993 (Globe/TM)\n09985987739 (Smart/TNT)"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.beneco_icon_removebg_preview,
                "Benguet Electric Cooperative\n (BENECO)",
                "637-4400\n09175921698 (Globe/TM)\n09088657202 (Smart/TNT)"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.emsbaguiocity_icon_removebg_preview,
                "Baguio City Emergency Medical\nServices (BCEMS)",
                "442-1911\n442-1901\n09055551911 (Globe/TM)\n09213208052 (Smart/TNT)"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.baguio_sbfp_icon_removebg,
                "Baguio City Fire Station",
                "422-2222\n443-7089\n09124096114 (Smart/TNT)"
            ) { /* Button logic */ },

            HomeScreen(
                R.drawable.bawadi_icon_removebg_preview,
                "Baguio Water District (BWD)",
                "442-3218\n442-5539\n09176794929 (Globe/TM)\n09088651504 (Smart/TNT)"
            ) { /* Button logic */ }
        )

        // Set Adapter
        rvhomescreen.adapter = HomeScreenAdapter(arrList)

        return view
    }
}
