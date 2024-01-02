package com.bignerdranch.android.blocknote

import android.app.Application

class BlockNoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}