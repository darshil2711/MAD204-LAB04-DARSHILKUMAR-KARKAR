package com.example.lab4notesreminderapp

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

class ReminderService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val noteTitle = intent?.getStringExtra("EXTRA_TITLE") ?: "Note"

        // Simulate a background task (wait 5 seconds)
        Handler(Looper.getMainLooper()).postDelayed({
            sendNotification(noteTitle)
            stopSelf() // Stop service after work is done
        }, 5000)

        return START_NOT_STICKY
    }

    private fun sendNotification(title: String) {
        val channelId = "reminder_channel"
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Note Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reminder: $title")
            .setContentText("Don't forget to check your note!")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}