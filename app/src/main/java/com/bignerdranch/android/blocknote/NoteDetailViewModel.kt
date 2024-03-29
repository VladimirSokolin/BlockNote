package com.bignerdranch.android.blocknote

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*



class NoteDetailViewModel: ViewModel() {
    private val noteRepository = NoteRepository.get()
    private val noteIdLiveData = MutableLiveData<UUID>()

    val noteLiveData: LiveData<Note?> =
        Transformations.switchMap(noteIdLiveData){noteId ->
            noteRepository.getNote(noteId)
        }

    fun loadNote(noteId: UUID){
        noteIdLiveData.value = noteId
    }

    fun saveNote(note: Note) {
        noteRepository.updateNote(note)
    }


}