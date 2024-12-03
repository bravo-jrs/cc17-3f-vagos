package com.example.alertifyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alertifyapp.R
import com.example.alertifyapp.model.NotificationContent

class NotificationAdapter(
    private var notifications: List<NotificationContent>, // Make it mutable
    private val onItemClickListener: (NotificationContent) -> Unit // Adding the listener here
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int = notifications.size

    // Method to update the notifications list and notify the adapter to refresh the RecyclerView
    fun updateNotifications(newNotifications: List<NotificationContent>) {
        notifications = newNotifications
        notifyDataSetChanged() // Notify that data has changed and RecyclerView should refresh
    }

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.notificationTitle)
        private val text: TextView = itemView.findViewById(R.id.notificationText)

        init {
            // Handling the click event on the whole item view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener(notifications[position]) // Triggering the listener
                }
            }
        }

        fun bind(notification: NotificationContent) {
            title.text = notification.title
            text.text = notification.text
        }
    }
}
