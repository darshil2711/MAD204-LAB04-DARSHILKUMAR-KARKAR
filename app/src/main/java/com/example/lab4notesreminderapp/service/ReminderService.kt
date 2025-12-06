package com.example.lab4notesreminderapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.lab4notesreminderapp.R
import kotlinx.coroutines.*

class ReminderService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val CHANNEL_ID = "ReminderChannel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        scope.launch {
            // Delay for 10 seconds before showing notification
            delay(10000)

            val notification = NotificationCompat.Builder(this@ReminderService, CHANNEL_ID)
                .setContentTitle("Notes Reminder")
                .setContentText("Don't forget to check your notes!")
                .setSmallIcon(R.drawable.ic_notification) // Ensure this drawable exists
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)

            // Stop the service after the task is done
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun createNotificationChannel() {
        val name = "Reminder Channel"
        val descriptionText = "Channel for note reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not a bound service
    }
}
