package com.example.memento.finatra

import com.example.memento.finatra.testkit.drivers.{FinagleHttpRequestExecutor, NotesControllerDriver}
import com.example.memento.finatra.testkit.matchers.ResponseMatchers
import org.specs2.mutable.Specification

/**
 * End-to-end Test for NotesController
 */
class NotesControllerEndToEndTest extends Specification with NotesControllerDriver with FinagleHttpRequestExecutor with ResponseMatchers {

  "Notes controller" should {
    "add a note and then successfully return in" in {
      val response = anAddNoteRequest.withText("Hello world!").execute()
      response must beOk
      response.noteId must not beEmpty

      val note = aGetNoteRequest.withId(response.noteId).execute()
      note must beOk
      note.text must_== "Hello world!"
    }
  }
}
