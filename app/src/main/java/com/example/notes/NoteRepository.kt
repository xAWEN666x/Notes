package com.example.notes

class NoteRepository {

    private val notes = mutableListOf<Note>()

    init {
        // Загрузка начальных данных (по желанию)
        // notes.add(Note(1, "Заголовок 1", "Содержание заметки 1"))
        // notes.add(Note(2, "Заголовок 2", "Содержание заметки 2"))
    }

    fun addNote(note: Note) {
        // Добавление заметки
        notes.add(note)
    }

    fun getAllNotes(): List<Note> {
        // Получение всех заметок
        return notes.toList()
    }
}