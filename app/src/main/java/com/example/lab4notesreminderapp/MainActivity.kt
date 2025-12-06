package com.example.lab4notesreminderapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text2.input.insert
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4notesreminderapp.data.Note
import com.example.lab4notesreminderapp.databinding.ActivityMainBinding
import com.example.lab4notesreminderapp.services.ReminderService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()

    // ActivityResultLauncher for notification permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startReminderService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = NoteListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this) { notes ->
            notes?.let { adapter.submitList(it) }
        }

        binding.fabAddNote.setOnClickListener {
            val noteTitle = "Test Note " + System.currentTimeMillis() / 1000
            val noteContent = "This is a test note."
            noteViewModel.insert(Note(title = noteTitle, content = noteContent))
        }

        // Ask for permission and start service
        askForNotificationPermission()
    }

    private fun askForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                startReminderService()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            startReminderService() // No permission needed for older versions
        }
    }

    private fun startReminderService() {
        val intent = Intent(this, ReminderService::class.java)
        startService(intent)
    }
}
