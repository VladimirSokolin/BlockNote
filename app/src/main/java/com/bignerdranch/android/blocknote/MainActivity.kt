package com.bignerdranch.android.blocknote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.CallBacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame)

        if(currentFragment == null){
            val fragment = ChronometerFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame,fragment)
                .commit()
        }

    }

    override fun onNoteSelected(noteId: UUID) {
        val fragment = NoteListFragment.newInstance(noteId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }
}