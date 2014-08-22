package com.example.memento.finatra

import com.example.memento.testkit.drivers.NotesControllerDriver
import com.example.memento.testkit.drivers.matchers.ResponseMatchers
import org.specs2.mutable.Specification

class NotesControllerEndToEndTest extends Specification with NotesControllerDriver with ResponseMatchers {

  "Notes controller" should {
    "add a note and then successfully return in" in {
      val result = anAddNoteRequestFor(text = "Hello world!").execute()
      result must beOk
      result.noteId must not beEmpty
    }
  }
}
