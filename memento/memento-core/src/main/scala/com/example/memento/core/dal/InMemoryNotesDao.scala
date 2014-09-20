package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note}
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

// TODO: use NoteEntity for storing date_created, etc.
class InMemoryNotesDao(implicit context: ExecutionContext) extends NotesDao {

  private val map = mutable.Map[UUID, Note]()

  def add(newNote: NewNote): Future[UUID] = {
    val id = UUID.randomUUID
    val note = Note(id, newNote.text)
    map += id -> note
    Future.successful(id)
  }

  def get(id: UUID): Future[Option[Note]] = Future.successful(map.get(id))
}
