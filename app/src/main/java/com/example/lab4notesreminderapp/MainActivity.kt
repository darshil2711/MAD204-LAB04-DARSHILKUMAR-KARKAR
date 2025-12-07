package com.example.lab4notesreminderapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: NotesDatabase
    private lateinit var adapter: NotesAdapter
    private lateinit var receiver: AppReceiver
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = NotesDatabase.getDatabase(this)

        // 1. Setup Broadcast Receiver (Fixed for Android 14+)
        receiver = AppReceiver()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }

        // 2. Setup UI
        recyclerView = findViewById(R.id.recyclerViewNotes)
        emptyView = findViewById(R.id.textViewEmpty)
        val fab = findViewById<FloatingActionButton>(R.id.fabAddNote)

        // 3. Setup Adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter(emptyList()) { note ->
            val intent = Intent(this, AddEditNoteActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // 4. Setup Swipe to Delete
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // FIXED: Use bindingAdapterPosition instead of deprecated adapterPosition
                val position = viewHolder.bindingAdapterPosition

                // Safety check to prevent crashing if the item is invalid
                if (position != RecyclerView.NO_POSITION) {
                    val noteToDelete = adapter.getNoteAt(position)

                    lifecycleScope.launch {
                        db.noteDao().delete(noteToDelete)
                        loadNotes() // Refresh list

                        // Undo logic
                        Snackbar.make(recyclerView, "Note deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                lifecycleScope.launch {
                                    db.noteDao().insert(noteToDelete)
                                    loadNotes()
                                }
                            }.show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        // 5. FAB Click Listener
        fab.setOnClickListener {
            startActivity(Intent(this, AddEditNoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            val notesList = db.noteDao().getAllNotes()
            adapter.updateList(notesList)

            if (notesList.isEmpty()) {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }
}