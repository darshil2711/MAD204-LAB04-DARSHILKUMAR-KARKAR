/**
 * MAD204-01 - Lab 4
 * Author: Darshilkumar Karkar (A00203357)
 * Date: 07/12/2025
 * Description: Data Access Object (DAO) interface. Defines the methods for interacting
 * with the database (Insert, Update, Delete, and Query all notes).
 */
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