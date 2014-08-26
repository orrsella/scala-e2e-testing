package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note, NoteId}
import scala.concurrent.Future

trait NotesDao {
  def add(newNote: NewNote): Future[NoteId]
  def get(id: NoteId): Future[Option[Note]]
}
