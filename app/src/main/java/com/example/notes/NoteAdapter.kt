package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface OnNoteInteractionListener {
        fun onEdit(note: Note)
        fun onViewDetail(note: Note)
    }

    private var onNoteInteractionListener: OnNoteInteractionListener? = null

    fun setOnNoteInteractionListener(listener: OnNoteInteractionListener) {
        this.onNoteInteractionListener = listener
    }

    var onEditClickListener: ((Note) -> Unit)? = null

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val titleEditText: EditText = itemView.findViewById(R.id.editTextTitle)
        val contentEditText: EditText = itemView.findViewById(R.id.editTextContent)

        init {
            // Пример обработки нажатия на элемент списка
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedNote = notes[position]
                    onNoteInteractionListener?.onViewDetail(clickedNote)
                }
            }
        }
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

        // Обработка нажатия на CardView для редактирования
        holder.cardView.setOnClickListener {
            onNoteInteractionListener?.onEdit(currentNote)
        }

        holder.itemView.setOnClickListener {
            // Оповестить слушателя о нажатии для отображения деталей заметки
            onNoteInteractionListener?.onViewDetail(currentNote)
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
