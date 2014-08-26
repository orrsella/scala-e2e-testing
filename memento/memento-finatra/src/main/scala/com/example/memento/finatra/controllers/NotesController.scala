package com.example.memento.finatra.controllers

import com.example.memento.core.concurrent.Implicits._
import com.example.memento.core.dal.NotesDao
import com.example.memento.core.model.NewNote
import com.example.memento.core.service.NotesService
import scala.concurrent.ExecutionContext.Implicits.global

class NotesController(notesDao: NotesDao) extends BaseController {

  private val service = new NotesService(notesDao)

  post("/notes") { req =>
    val request = parseBody[AddNoteRequest](req)
    val newNote = NewNote(request.text)
    service.addNote(newNote).map(render.json)
  }
}

case class AddNoteRequest(text: String)
case class AddNoteResponse(noteId: String)
