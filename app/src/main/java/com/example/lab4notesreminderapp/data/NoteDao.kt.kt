package com.example.lab4notesreminderapp.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long


    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>


    @Update
    suspend fun update(note: Note)


    @Delete
    suspend fun delete(note: Note)


    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Long): Note?
}