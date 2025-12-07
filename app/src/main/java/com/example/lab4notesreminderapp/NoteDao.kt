package com.example.lab4notesreminderapp

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>
}