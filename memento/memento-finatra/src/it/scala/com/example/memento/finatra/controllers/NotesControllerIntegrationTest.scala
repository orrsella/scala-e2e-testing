package com.example.memento.finatra.controllers

import com.example.memento.core.dal.InMemoryNotesDao
import com.twitter.finatra.FinatraServer
import com.twitter.finatra.test.SpecHelper
import org.specs2.mutable.Specification

// acceptance-test for NotesController
class NotesControllerIntegrationTest extends Specification with SpecHelper {

  val notesDao = new InMemoryNotesDao
  val controller = new NotesController(notesDao)
  val server = new FinatraServer
  server.register(controller)

  "POST /notes" should {
    "respond 200" in {
      post("/notes", body = "{ \"text2\": \"Hello\" }")
      response.code must_== 200
    }
  }
}
