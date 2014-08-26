package com.example.memento.finatra

import com.example.memento.finatra.testkit.drivers.NotesControllerDriver
import com.example.memento.finatra.testkit.matchers.ResponseMatchers
import org.specs2.mutable.Specification

class NotesControllerEndToEndTest extends Specification with NotesControllerDriver with ResponseMatchers {

  "Notes controller" should {
    "add a note and then successfully return in" in {
      val response = anAddNoteRequestFor(text = "Hello world!").execute()
      response must beOk
      response.noteId must not beEmpty

      val note = aGetNoteRequestFor(id = response.noteId).execute()
      note must beOk
      note.text must_== "Hello world"
    }
  }
}
