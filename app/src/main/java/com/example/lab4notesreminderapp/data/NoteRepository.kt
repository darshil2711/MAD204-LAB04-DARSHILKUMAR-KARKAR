import androidx.compose.foundation.text2.input.delete
import androidx.compose.foundation.text2.input.insert
import com.example.lab4notesreminderapp.data.Note
import com.example.lab4notesreminderapp.data.NoteDao

package com.example.lab4notesreminderapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun getNoteById(id: Int): Flow<Note?> {
        return noteDao.getNoteById(id)
    }
}
