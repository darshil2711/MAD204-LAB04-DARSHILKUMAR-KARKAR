package com.example.lab4notesreminderapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text2.input.insert
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4notesreminderapp.data.Note
import com.example.lab4notesreminderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val noteViewModel: NoteViewModel by viewModels()

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

        // Dummy data for testing
        binding.fabAddNote.setOnClickListener {
            val noteTitle = "Test Note " + System.currentTimeMillis() / 1000
            val noteContent = "This is a test note."
            noteViewModel.insert(Note(title = noteTitle, content = noteContent))
        }
    }
}
