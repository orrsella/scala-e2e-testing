package com.example.memento.core.dal

import com.example.memento.core.model.Note
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class InMemoryNotesDao(implicit context: ExecutionContext) extends NotesDao {

  private val map = mutable.Map[UUID, Note]()

  def add(text: String): Future[UUID] = {
    val id = UUID.randomUUID
    val note = Note(id, text)
    map += id -> note
    Future.successful(id)
  }

  def get(id: UUID): Future[Option[Note]] = Future.successful(map.get(id))
}
