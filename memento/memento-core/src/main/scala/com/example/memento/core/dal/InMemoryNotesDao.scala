package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note, NoteId}
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

// TODO: use NoteEntity for storing date_created, etc.
class InMemoryNotesDao(implicit context: ExecutionContext) extends NotesDao {

  private val map = mutable.Map[NoteId, Note]()

  def add(newNote: NewNote): Future[NoteId] = {
    val id = NoteId(UUID.randomUUID)
    val note = Note(id, newNote.text)
    map += id -> note
    Future.successful(id)
  }

  def get(id: NoteId): Future[Option[Note]] = Future.successful(map.get(id))
}
