package com.example.memento.core.service

import com.example.memento.core.dal.NotesDao
import com.example.memento.core.model.Note
import com.example.memento.core.translation.Translator
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class NotesService(notesDao: NotesDao, translator: Translator)(implicit context: ExecutionContext) {

  def addNote(text: String): Future[UUID] = notesDao.add(text)
  def getNote(id: UUID): Future[Option[Note]] = notesDao.get(id)

  def translateNote(id: UUID, lang: String): Future[Option[Note]] = {
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
