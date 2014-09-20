//package com.example.memento.core.dal
//
//import java.util.UUID
//
//import com.example.memento.core.model.{Note, NewNote}
//import com.example.memento.core.testkit.EmbeddedElasticSearchServer
//import org.specs2.mutable.Specification
//import org.specs2.specification.Scope
//
//class ElasticSearchNotesDaoIntegrationTest extends Specification {
//
//  private val server = new EmbeddedElasticSearchServer
//
//  trait Context extends Scope {
//    val dao = new ElasticSearchNotesDao(server.client)
//  }
//
//  step {
//    server.start()
//    server.client.admin.indices.prepareCreate("notes").execute.actionGet()
////    server.client.admin.indices.preparePutMapping("notes").setType("note").setSource("{\"note\":{}}").execute.actionGet()
//  }
//
//  "ElasticSearch notes dao" should {
//    "return None for a non-existing note" in new Context {
//      dao.get(UUID.randomUUID) must beNone.await
//    }
//
//    "add and return a note" in new Context {
//      val newNote = NewNote("Hello world")
//      dao.add(newNote) map { id =>
//        val note = dao.get(id)
//        note must beSome[Note].await
//        val x = 10
//      }
//    }
//  }
//
//  step {
//    server.stop()
//  }
//}
