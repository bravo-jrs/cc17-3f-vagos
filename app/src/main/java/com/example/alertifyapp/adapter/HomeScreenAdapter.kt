package com.example.alertifyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.R
import com.example.alertifyapp.model.HomeScreen

class HomeScreenAdapter(val data: List<HomeScreen>): RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>() {

    class HomeScreenViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val homeScreenImage = row.findViewById<ImageView>(R.id.redphone)
        val homeScreenInfo = row.findViewById<TextView>(R.id.redphone_info)
        val homeScreenDesc = row.findViewById<TextView>(R.id.redphone_info2)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeScreenAdapter.HomeScreenViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.homescreen_item_view, parent, false)
        return HomeScreenViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeScreenAdapter.HomeScreenViewHolder, position: Int) {
        holder.homeScreenImage.setImageResource(data.get(position).imageResId)
        holder.homeScreenInfo.text = data.get(position).name
        holder.homeScreenDesc.text = data.get(position).desc
    }


}