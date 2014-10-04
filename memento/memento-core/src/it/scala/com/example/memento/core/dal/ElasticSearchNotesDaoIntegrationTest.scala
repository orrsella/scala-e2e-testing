package com.example.memento.core.dal

import com.example.memento.core.model.Note
import com.example.memento.testkit.FutureTestingSupport
import com.example.memento.testkit.matchers.NoteMatchers
import com.example.memento.testkit.servers.ElasticSearchServer
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class ElasticSearchNotesDaoIntegrationTest extends Specification with FutureTestingSupport with NoteMatchers {

  val server = new ElasticSearchServer
  val index = "notes"

  trait Context extends Scope {
    val dao = new ElasticSearchNotesDao(server.client)
  }

  step {
    server.start()
    server.client.admin.indices.prepareCreate(index).execute.actionGet()
    server.client.admin.cluster.prepareHealth(index).setWaitForActiveShards(1).execute.actionGet()
//    server.client.admin.indices.preparePutMapping("notes").setType("note").setSource("{\"note\":{}}").execute.actionGet()
  }

  "ElasticSearch notes dao" should {
    "return None for a non-existing note" in new Context {
      val note: Option[Note] = dao.get(UUID.randomUUID)
      note must beNone
    }

    "add and return a note" in new Context {
      val id: UUID = dao.add("Hello world")
      val note: Option[Note] = dao.get(id)
      note must beSome[Note]
      note must haveText("Hello world")
    }
  }

  step {
    server.stop()
  }
}
