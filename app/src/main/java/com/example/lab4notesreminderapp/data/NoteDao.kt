package com.example.lab4notesreminderapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note?>

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>
}
