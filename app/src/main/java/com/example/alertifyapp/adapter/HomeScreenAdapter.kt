package com.example.alertifyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.R
import com.example.alertifyapp.model.HomeScreen

class HomeScreenAdapter(private val data: List<HomeScreen>) : RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>() {

    // ViewHolder class to hold the views for each item in the list
    class HomeScreenViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        // You can now store row as a class property if you want it to be accessed elsewhere in the class
        val homeScreenImage: ImageView = row.findViewById(R.id.redphone)
        val homeScreenInfo: TextView = row.findViewById(R.id.redphone_info)
        val homeScreenDesc: TextView = row.findViewById(R.id.redphone_info2)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        // Inflate the layout for each item in the list
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.homescreen_item_view, parent, false)
        return HomeScreenViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        // Bind data to views
        holder.homeScreenImage.setImageResource(data[position].imageResId)
        holder.homeScreenInfo.text = data[position].name
        holder.homeScreenDesc.text = data[position].desc
    }
}
