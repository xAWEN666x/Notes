package com.example.notes

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date
import android.view.LayoutInflater
import java.text.SimpleDateFormat
import java.util.Locale

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

        noteAdapter.setOnNoteInteractionListener(object : NoteAdapter.OnNoteInteractionListener {
            override fun onEdit(note: Note) {
                showEditDialog(note)
            }

            override fun onViewDetail(note: Note) {
                showNoteDetailDialog(note)
            }
        })

        binding.buttonAddNote.setOnClickListener {
            val noteText = binding.editTextNote.text.toString()
            if (noteText.isNotEmpty()) {
                val newNote = Note(
                    id = noteRepository.getAllNotes().size + 1,
                    title = "Note Title", // Заголовок по умолчанию (можете изменить)
                    content = noteText,
                    date = getCurrentDate() // Используйте функцию getCurrentDate для получения текущей даты и времени
                )
                noteRepository.addNote(newNote)
                noteAdapter.updateNotes(noteRepository.getAllNotes())
                binding.editTextNote.text = null
            }
        }

    }
    private fun getCurrentDate(): Date {
        return Date()
    }

    private fun showNoteDetailDialog(note: Note) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_note_detail, null)
        val titleTextView = dialogView.findViewById<TextView>(R.id.textViewTitle)
        val contentTextView = dialogView.findViewById<TextView>(R.id.textViewContent)
        val dateTextView = dialogView.findViewById<TextView>(R.id.textViewDate)

        titleTextView.text = note.title
        contentTextView.text = note.content

        // Используйте SimpleDateFormat для форматирования даты и времени
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(note.date)
        dateTextView.text = "Created on: $formattedDate"

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        dialog.show()
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