

package com.bignerdranch.android.blocknote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import java.util.*
import androidx.lifecycle.Observer

private const val ARG_NOTE_ID = "note_id"
private const val DIALOG_DATE = "dialog_date"
private const val REQUEST_DATE = 0

class NoteFragment: Fragment(), DatePickerFragment.Callbacks {
    private lateinit var note: Note
    private lateinit var titleField: EditText
    private lateinit var valueField: EditText
    private lateinit var tvDate: TextView
    private val noteDetailViewModel: NoteDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()

        val noteId: UUID = arguments?.getSerializable(ARG_NOTE_ID) as UUID
         noteDetailViewModel.loadNote(noteId)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        titleField = view.findViewById(R.id.editTextTitle)
        valueField = view.findViewById(R.id.editTextValue)
        tvDate = view.findViewById(R.id.tvDate)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let{
                    this.note = note
                    updateUI()
                }
            }
        )
    }


    fun updateUI(){
        titleField.setText(note.title)
        valueField.setText(note.value)
        tvDate.text = note.date.toString()
    }

    override fun onStop() {
        super.onStop()
        noteDetailViewModel.saveNote(note)
    }

    override fun onStart() {
        super.onStart()
        val titleWatch = object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                note.title = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        val valueWatch = object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                note.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        titleField.addTextChangedListener(titleWatch)
        valueField.addTextChangedListener(valueWatch)

        tvDate.setOnClickListener{
            DatePickerFragment.newInstance(note.date).apply {
                show(this@NoteFragment.requireFragmentManager(), DIALOG_DATE)
                setTargetFragment(this@NoteFragment, REQUEST_DATE)
            }
        }
    }

    override fun onDateSelected(date: Date) {
        note.date = date
        tvDate.text = note.date.toString()
    }
}