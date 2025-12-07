package com.example.lab4notesreminderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private var notes: List<Note>,
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val content: TextView = itemView.findViewById(R.id.textViewContent)

        fun bind(note: Note) {
            title.text = note.title
            content.text = note.content
            itemView.setOnClickListener { onNoteClick(note) }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    // Helper to update the list
    fun updateList(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    // NEW: Helper to get a note at a specific position for deletion
    fun getNoteAt(position: Int): Note {
        return notes[position]
    }
}