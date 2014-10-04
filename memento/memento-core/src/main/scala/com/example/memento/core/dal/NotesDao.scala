package com.example.memento.core.dal

import com.example.memento.core.model.Note
import java.util.UUID
import scala.concurrent.Future

trait NotesDao {
  def add(text: String): Future[UUID]
  def get(id: UUID): Future[Option[Note]]
}
