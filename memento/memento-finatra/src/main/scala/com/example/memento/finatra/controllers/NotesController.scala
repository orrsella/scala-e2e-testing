package com.example.memento.finatra.controllers

import com.example.memento.core.concurrent.Implicits._
import com.example.memento.core.dal.NotesDao
import com.example.memento.core.model.{NewNote, NoteId}
import com.example.memento.core.service.NotesService
import com.example.memento.finatra.exceptions.NoteNotFoundException
import com.example.memento.finatra.requests.AddNoteRequest
import com.example.memento.finatra.responses.{AddNoteResponse, GetNoteResponse}
import com.twitter.finatra.Request
import scala.concurrent.ExecutionContext.Implicits.global

class NotesController(notesDao: NotesDao) extends BaseController {

  private val service = new NotesService(notesDao)

  post("/notes") { req =>
    val request = req.bodyAs[AddNoteRequest]
    val newNote = NewNote(request.text)

    service.addNote(newNote) map { id =>
      render.json(new AddNoteResponse(id))
    }
  }

  get("/notes/:id") { req: Request =>
    val id = NoteId(req.getRouteParam("id"))

    service.getNote(id) map { opt =>
      val note = opt.getOrElse(throw new NoteNotFoundException(id))
      render.json(new GetNoteResponse(note))
    }
  }
}
