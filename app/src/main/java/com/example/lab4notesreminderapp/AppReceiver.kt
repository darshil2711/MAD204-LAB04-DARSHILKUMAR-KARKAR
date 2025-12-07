package com.example.lab4notesreminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AppReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Toast.makeText(context, "Airplane Mode Changed!", Toast.LENGTH_SHORT).show()
        }
    }
}