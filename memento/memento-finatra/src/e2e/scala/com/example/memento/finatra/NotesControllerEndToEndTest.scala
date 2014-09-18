package com.example.memento.finatra

import com.example.memento.finatra.testkit.drivers.{FinagleHttpRequestExecutor, NotesControllerDriver}
import com.example.memento.finatra.testkit.matchers.ResponseMatchers
import org.specs2.mutable.Specification

/**
 * End-to-end Test for NotesController
 */
class NotesControllerEndToEndTest
  extends Specification
  with NotesControllerDriver
  with FinagleHttpRequestExecutor
  with ResponseMatchers {

  "Notes controller" should {
    "add a note and then successfully get it" in {
      val addResp = anAddNoteRequest.withText("Hello world!").execute()
      addResp must beOk
      addResp.noteId must not beEmpty

      val getResp = aGetNoteRequest.withId(addResp.noteId).execute()
      getResp must beOk
      getResp.text must_== "Hello world!"
    }

    "add a note and then get it translated" in {
      val addResp = anAddNoteRequest.withText("Good morning").execute()
      val translated = aTranslateNoteRequest.withId(addResp.noteId).withLang("es").execute()
      translated must beOk
      translated.text must_== "Buenos días"
    }
  }
}
