package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var onEditClickListener: ((Note) -> Unit)? = null

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val titleEditText: EditText = itemView.findViewById(R.id.editTextTitle)
        val contentEditText: EditText = itemView.findViewById(R.id.editTextContent)
        val editImageView: ImageView = itemView.findViewById(R.id.imageViewEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.titleTextView.text = currentNote.title
        holder.titleEditText.setText(currentNote.title)
        holder.contentEditText.setText(currentNote.content)

        // Обработка нажатия на кнопку редактирования
        holder.editImageView.setOnClickListener {
            onEditClickListener?.invoke(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}


