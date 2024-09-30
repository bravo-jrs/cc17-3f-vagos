package com.example.alertify.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alertify.R
import com.example.alertify.model.HomePage


class HomePageAdapter(val data: List<HomePage>):
    RecyclerView.Adapter<HomePageAdapter.HomePageItemViewHolder>(){

    class HomePageItemViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val homePageImageView = row.findViewById<ImageView>(R.id.home_page_1)
        val homePageTextView = row.findViewById<TextView>(R.id.Hotline_text)
        val homePageDescView = row.findViewById<TextView>(R.id.Hotline_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.home_menu_item_view, parent, false)
        return HomePageItemViewHolder(layout)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomePageItemViewHolder, position: Int) {
        holder.homePageImageView.setImageResource(data.get(position).imageResId)
        holder.homePageTextView.text = data.get(position).name
        holder.homePageDescView.text = data.get(position).desc

    }

}