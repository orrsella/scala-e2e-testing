package com.example.memento.core.dal

import com.example.memento.core.model.{Note, NewNote, NoteId}
import com.example.memento.core.testkit.EmbeddedElasticSearchServer
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class ElasticSearchNotesDaoIntegrationTest extends Specification {

  private val server = new EmbeddedElasticSearchServer

  trait Context extends Scope {
    val dao = new ElasticSearchNotesDao(server.client)
  }

  step {
    server.start()
    server.client.admin.indices.prepareCreate("notes").execute.actionGet()
  }

  "ElasticSearch notes dao" should {
    "return None for a non-existing note" in new Context {
      dao.get(NoteId.random) must beNone.await
    }

    "add and return a note" in new Context {
      val newNote = NewNote("Hello world")
      dao.add(newNote) map { id =>
        val note = dao.get(id)
        note must beSome[Note].await
      }
    }
  }

  step {
    server.stop()
  }
}
