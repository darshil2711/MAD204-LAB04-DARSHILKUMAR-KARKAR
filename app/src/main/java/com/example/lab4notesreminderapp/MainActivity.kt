package com.example.lab4notesreminderapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4notesreminderapp.data.Note
import com.example.lab4notesreminderapp.data.NotesDatabase
import com.example.lab4notesreminderapp.data.NoteRepository
import com.example.lab4notesreminderapp.ui.NoteListAdapter
import com.example.lab4notesreminderapp.ui.AddNoteFragment
import com.example.lab4notesreminderapp.ui.NoteDetailFragment
import com.example.lab4notesreminderapp.databinding.ActivityMainBinding
import com.example.lab4notesreminderapp.service.ReminderService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), NoteListAdapter.OnNoteClickListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteListAdapter
    private lateinit var viewModelFactory: com.example.lab4notesreminderapp.data.NoteViewModelFactory
    private lateinit var viewModel: com.example.lab4notesreminderapp.data.NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


// Setup database + repository + viewmodel
        val dao = NotesDatabase.getInstance(applicationContext).noteDao()
        val repository = NoteRepository(dao)
        viewModelFactory = com.example.lab4notesreminderapp.data.NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[com.example.lab4notesreminderapp.data.NoteViewModel::class.java]


        adapter = NoteListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        viewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }


        binding.fabAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddNoteFragment())
                .addToBackStack(null)
                .commit()
        }


// Start reminder service (simple demonstration)
        val intent = Intent(this, ReminderService::class.java)
        startService(intent)
    }


    override fun onNoteClick(note: Note) {
        val frag = NoteDetailFragment.newInstance(note.id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, frag)
            .addToBackStack(null)
            .commit()
    }
}