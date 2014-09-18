package com.example.memento.core.service

import com.example.memento.core.dal.NotesDao
import com.example.memento.core.model.{Note, NewNote, NoteId}
import com.example.memento.core.translation.Translator
import scala.concurrent.{ExecutionContext, Future}

class NotesService(notesDao: NotesDao, translator: Translator)(implicit context: ExecutionContext) {

  def addNote(newNote: NewNote): Future[NoteId] = notesDao.add(newNote)
  def getNote(id: NoteId): Future[Option[Note]] = notesDao.get(id)

  def translateNote(id: NoteId, lang: String): Future[Option[Note]] = {
    for {
      note <- notesDao.get(id)
      translated <- translate(note, lang)
    } yield combine(translated, note)
  }

  private def translate(note: Option[Note], lang: String): Future[Option[String]] = note match {
    case Some(n) => translator.translate(n.text, lang).map(t => Some(t))
    case None => Future.successful(None)
  }

  private def combine(translated: Option[String], note: Option[Note]): Option[Note] = (translated, note) match {
    case (Some(t), Some(n)) => Some(Note(n.id, t))
    case _ => None
  }
}
