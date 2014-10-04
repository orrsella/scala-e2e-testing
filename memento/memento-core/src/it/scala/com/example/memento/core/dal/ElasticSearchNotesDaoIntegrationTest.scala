package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note}
import com.example.memento.testkit.servers.ElasticSearchServer
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ElasticSearchNotesDaoIntegrationTest extends Specification with FutureTestingSupport {

  private val server = new ElasticSearchServer

  trait Context extends Scope {
    val dao = new ElasticSearchNotesDao(server.client)
  }

  step {
    server.start()
    server.client.admin.indices.prepareCreate("notes").execute.actionGet()
//    server.client.admin.indices.preparePutMapping("notes").setType("note").setSource("{\"note\":{}}").execute.actionGet()
  }

  "ElasticSearch notes dao" should {
    "return None for a non-existing note" in new Context {
      val note: Option[Note] = dao.get(UUID.randomUUID)
      note must beNone
    }

    "add and return a note" in new Context {
      val newNote = NewNote("Hello world")
      val id: UUID = dao.add(newNote)
      val note: Option[Note] = dao.get(id)
      Thread.sleep(1000 * 60 * 3)
      note must beSome[Note]
    }
  }

  step {
    server.stop()
  }

}

trait FutureTestingSupport {
  val timeout: Duration = 1000.milli
  implicit def futureToValue[T](f: Future[T]): T = Await.result(f, timeout)
}
