package com.bignerdranch.android.blocknote

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_NOTE_ID = "note_id"
class NoteListFragment: Fragment() {

    interface CallBacks{
        fun onNoteSelected(noteId: UUID)
    }
    companion object {
        fun newInstance(note: UUID): NoteFragment {
            val args = Bundle().apply {
                putSerializable(ARG_NOTE_ID, note)
            }
            return NoteFragment().apply {
                arguments = args
            }
        }
    }

    private var callBacks: CallBacks? = null
    private val noteListViewModel: NoteListViewModel by viewModels()
    private lateinit var noteListFragment: RecyclerView
    private var adapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBacks = context as CallBacks?
    }

    override fun onDetach() {
        super.onDetach()
        callBacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note_list,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_note -> {
                val note = Note()
                noteListViewModel.addNote(note)
                callBacks?.onNoteSelected(note.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_note, container, false)

        noteListFragment = view.findViewById(R.id.noteListFragment) as RecyclerView

        noteListFragment.layoutManager = LinearLayoutManager(context)

        noteListFragment.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel.noteListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { notes ->
                notes?.let{updateUI(notes)}
            }
        )
    }

    private fun updateUI(notes: List<Note>) {
        adapter = NoteAdapter(notes)
        noteListFragment.adapter = adapter
    }

    private inner class NoteHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var note: Note
        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val btDelete = view.findViewById<ImageButton>(R.id.btDelete)

        init {
            itemView.setOnClickListener(this)
            btDelete.setOnClickListener {
                noteListViewModel.deleteNote(note)
            }

        }

        fun bind(note: Note){
            this.note = note
            tvTitle.text = this.note.title
        }



        override fun onClick(p0: View?) {
            callBacks?.onNoteSelected(note.id)
        }
    }

    private inner class NoteAdapter(var notes: List<Note>) : RecyclerView.Adapter<NoteHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.note_element, parent, false)
            return NoteHolder(view)
        }

        override fun getItemCount(): Int=notes.size

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.bind(note)
        }
    }



}