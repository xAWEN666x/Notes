package com.example.notes

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteRepository = NoteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(noteRepository.getAllNotes())
        binding.recyclerViewNotes.adapter = noteAdapter

        noteAdapter.onEditClickListener = { note ->
            showEditDialog(note)
        }

        binding.buttonAddNote.setOnClickListener {
            val noteText = binding.editTextNote.text.toString()
            if (noteText.isNotEmpty()) {
                val newNote = Note(
                    id = noteRepository.getAllNotes().size + 1,
                    title = "Note Title", // Заголовок по умолчанию (можете изменить)
                    content = noteText
                )
                noteRepository.addNote(newNote)
                noteAdapter.updateNotes(noteRepository.getAllNotes())
                binding.editTextNote.text = null
            }
        }
    }

    private fun showEditDialog(note: Note) {
        val view = layoutInflater.inflate(R.layout.dialog_edit_note, null)
        val titleEditText = view.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = view.findViewById<EditText>(R.id.editTextContent)

        titleEditText.setText(note.title)
        contentEditText.setText(note.content)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = titleEditText.text.toString()
                val newContent = contentEditText.text.toString()

                note.title = newTitle
                note.content = newContent
                noteAdapter.updateNotes(noteRepository.getAllNotes())
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
