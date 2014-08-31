package com.example.memento.finatra.responses

import com.example.memento.core.model.Note

class GetNoteResponse(note: Note) {
  val id = note.id
  val text = note.text
}
