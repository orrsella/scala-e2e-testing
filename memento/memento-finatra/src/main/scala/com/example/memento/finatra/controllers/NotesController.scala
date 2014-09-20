package com.example.memento.finatra.controllers

import com.example.memento.core.concurrent.Implicits._
import com.example.memento.core.dal.NotesDao
import com.example.memento.core.exceptions.InvalidNoteIdException
import com.example.memento.core.model.NewNote
import com.example.memento.core.service.NotesService
import com.example.memento.core.translation.Translator
import com.example.memento.finatra.exceptions.NoteNotFoundException
import com.example.memento.finatra.requests.AddNoteRequest
import com.example.memento.finatra.responses.{AddNoteResponse, GetNoteResponse}
import com.twitter.finatra.Request
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

class NotesController(notesDao: NotesDao, translator: Translator) extends BaseController {

  private val service = new NotesService(notesDao, translator)

  post("/notes") { req =>
    val request = req.bodyAs[AddNoteRequest]
    val newNote = NewNote(request.text)

    service.addNote(newNote) map { id =>
      render.json(new AddNoteResponse(id))
    }
  }

  get("/notes/:id") { req: Request =>
    val idParam = req.getRouteParam("id")
    val id = Try(UUID.fromString(idParam)).getOrElse(throw new InvalidNoteIdException(idParam, null))

    service.getNote(id) map { opt =>
      val note = opt.getOrElse(throw new NoteNotFoundException(id))
      render.json(new GetNoteResponse(note))
    }
  }

  get("/translate") { req =>
    val lang = req.getQueryParam("lang")
    val idParam = req.getQueryParam("noteId")
    val id = Try(UUID.fromString(idParam)).getOrElse(throw new InvalidNoteIdException(idParam, null))

    service.translateNote(id, lang) map { opt =>
      val note = opt.getOrElse(throw new NoteNotFoundException(id))
      render.json(new GetNoteResponse(note))
    }
  }
}
