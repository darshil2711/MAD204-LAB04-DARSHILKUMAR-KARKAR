package com.example.lab4notesreminderapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SystemEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d("SystemEventReceiver", "Received action: $action")

        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("SystemEventReceiver", "Device has booted. App can restart services if needed.")
                // Example: You could restart the ReminderService here
                // val serviceIntent = Intent(context, ReminderService::class.java)
                // context.startService(serviceIntent)
            }
            "android.net.conn.CONNECTIVITY_CHANGE" -> {
                Log.d("SystemEventReceiver", "Network connectivity changed.")
            }
        }
    }
}
