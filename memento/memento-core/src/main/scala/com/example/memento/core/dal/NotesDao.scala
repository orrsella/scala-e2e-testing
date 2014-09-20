package com.example.memento.core.dal

import java.util.UUID

import com.example.memento.core.model.{NewNote, Note}
import scala.concurrent.Future

trait NotesDao {
  def add(newNote: NewNote): Future[UUID]
  def get(id: UUID): Future[Option[Note]]
}
