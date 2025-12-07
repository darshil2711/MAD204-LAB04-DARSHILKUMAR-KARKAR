package com.example.lab4notesreminderapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Annotate the class to be a Room Database
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    // 2. Connect the Database to the DAO
    abstract fun noteDao(): NoteDao

    // 3. Create a Singleton (Standard Pattern for Room)
    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}