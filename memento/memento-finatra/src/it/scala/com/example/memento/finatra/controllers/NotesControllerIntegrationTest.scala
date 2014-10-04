//package com.example.memento.finatra.controllers
//
//import com.example.memento.core.dal.InMemoryNotesDao
//import com.example.memento.core.translation.InMemoryTranslator
//import com.example.memento.testkit.drivers.NotesControllerDriver
//import org.specs2.mutable.Specification
//
///**
//* Acceptance Test for NotesController
//*/
//class NotesControllerIntegrationTest extends Specification with ControllerIntegrationTest with NotesControllerDriver {
//  sequential
//
//  val notesDao = new InMemoryNotesDao
//  val translator = new InMemoryTranslator
//  val controller = new NotesController(notesDao, translator)
//
//  "Notes controller" should {
//    "return not found for a get non-existing note request" in {
//      val response = aGetNoteRequest.withId("fb293322-c23d-4ab7-8ca6-7a0bb0c18cac").execute()
//      response must beNotFound
//    }
//
//    "add and return a new note" in {
//      val response = anAddNoteRequest.withText("Hello world").execute()
//      response must beOk
//      response.noteId must not beEmpty
//
//      val note = aGetNoteRequest.withId(response.noteId).execute()
//      note must beOk
//      note.text must_== "Hello world"
//    }
//
//    "return bad request for a get note request with an invalid note id" in {
//      val response = aGetNoteRequest.withId("foo").execute()
//      response must beBadRequest
//    }
//  }
//}
