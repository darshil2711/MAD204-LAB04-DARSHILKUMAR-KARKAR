package com.example.lab4notesreminderapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4notesreminderapp.data.Note
import com.example.lab4notesreminderapp.databinding.ActivityMainBinding
import com.example.lab4notesreminderapp.services.ReminderService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()

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

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)

        val adapter = NoteListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this) { notes ->
            // Let the adapter know about the new list.
            adapter.submitList(notes)

            // Show the empty view if the list is empty, otherwise show the RecyclerView
            if (notes.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
            }
        }

        binding.fabAddNote.setOnClickListener {
            val noteTitle = "New Note " + (noteViewModel.allNotes.value?.size?.plus(1) ?: 1)
            val noteContent = "This is a sample note."
            noteViewModel.insert(Note(title = noteTitle, content = noteContent))
        }

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
