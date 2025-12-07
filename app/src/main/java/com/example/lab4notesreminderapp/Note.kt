/**
 * MAD204-01 - Lab 4
 * Author: Darshilkumar Karkar (A00203357)
 * Date: 07/12/2025
 * Description: Entity class representing the 'notes_table' in the Room database.
 * It defines the data structure for a single note (id, title, content).
 */
package com.example.lab4notesreminderapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)