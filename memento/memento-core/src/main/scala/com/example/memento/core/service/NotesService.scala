package com.example.memento.core.service

import com.example.memento.core.dal.NotesDao
import com.example.memento.core.model.{NewNote, NoteId}
import scala.concurrent.{ExecutionContext, Future}

class NotesService(notesDao: NotesDao)(implicit context: ExecutionContext) {

  def addNote(newNote: NewNote): Future[NoteId] = notesDao.add(newNote)
}
