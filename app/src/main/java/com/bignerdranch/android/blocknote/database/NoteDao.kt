package com.bignerdranch.android.blocknote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.blocknote.Note
import java.util.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id = (:id)")
    fun getNote(id:UUID): LiveData<Note?>

    @Update
    fun updateNote(note:Note)

    @Insert
    fun addNote(note: Note)

    @Delete
    fun delete(note: Note)

}