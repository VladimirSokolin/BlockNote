package com.bignerdranch.android.blocknote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.blocknote.Note

@Database(entities = [Note::class], version = 2)
@TypeConverters(NoteTypeConverters::class)
abstract  class NoteDatabase: RoomDatabase() {
    abstract  fun noteDao(): NoteDao
}
