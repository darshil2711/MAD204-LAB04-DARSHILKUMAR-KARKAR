/**
 * MAD204-01 - Lab 4
 * Author: Darshilkumar Karkar (A00203357)
 * Date: 07/12/2025
 * Description: Activity for creating or updating a note. It saves data to the Room DB
 * and starts the ReminderService to schedule a notification.
 */
package com.example.lab4notesreminderapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var db: NotesDatabase
    private var noteId = 0 // 0 means new note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        // Request Notification Permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        db = NotesDatabase.getDatabase(this)

        val editTitle = findViewById<TextInputEditText>(R.id.editTextTitle)
        val editContent = findViewById<TextInputEditText>(R.id.editTextContent)
        val btnSave = findViewById<Button>(R.id.buttonReminder)

        if (intent.hasExtra("id")) {
            noteId = intent.getIntExtra("id", 0)
            editTitle.setText(intent.getStringExtra("title"))
            editContent.setText(intent.getStringExtra("content"))
        }

        btnSave.setOnClickListener {
            val title = editTitle.text.toString()
            val content = editContent.text.toString()

            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(id = if (noteId == 0) 0 else noteId, title = title, content = content)

            lifecycleScope.launch {
                if (noteId == 0) {
                    db.noteDao().insert(note)
                } else {
                    db.noteDao().update(note)
                }

                val serviceIntent = Intent(this@AddEditNoteActivity, ReminderService::class.java)
                serviceIntent.putExtra("EXTRA_TITLE", title)
                startService(serviceIntent)

                Toast.makeText(this@AddEditNoteActivity, "Saved & Reminder Set!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}