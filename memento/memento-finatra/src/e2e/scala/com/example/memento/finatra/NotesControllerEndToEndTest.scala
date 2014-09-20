package com.example.memento.finatra

import com.example.memento.testkit.drivers.{FinagleHttpRequestExecutor, NotesControllerDriver}
import com.example.memento.testkit.matchers.ResponseMatchers
import com.example.memento.testkit.servers.http.FakeYandexTranslateServer
import org.specs2.mutable.Specification

/**
 * End-to-end Test for NotesController
 */
class NotesControllerEndToEndTest
  extends Specification
  with NotesControllerDriver
  with FinagleHttpRequestExecutor
  with ResponseMatchers {

  private val port = 9921
  private val server = new FakeYandexTranslateServer(port)

  step {
//    server.start()
  }

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

    "return not found for a get non-existing note request" in {
      val response = aGetNoteRequest.withId("fb293322-c23d-4ab7-8ca6-7a0bb0c18cac").execute()
      response must beNotFound
    }

    "return bad request for a get note request with an invalid note id" in {
      val response = aGetNoteRequest.withId("foo").execute()
      response must beBadRequest
    }
  }

  step {
//    server.stop()
  }
}
