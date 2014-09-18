package com.example.memento.core.dal

import com.example.memento.core.model.{NewNote, Note, NoteId}
import com.sksamuel.elastic4s.ElasticDsl.{get => esGet}
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.client.{Client, Requests}

import scala.concurrent.{ExecutionContext, Future, Promise}

class ElasticSearchNotesDao(client: Client)(implicit context: ExecutionContext) extends NotesDao {

  private val index = "notes"
  private val typ = "note"
//  private val client2: Client = ???

//  def this(client: Client)(implicit context: ExecutionContext) = this(ElasticClient.fromClient(client))

  def add(newNote: NewNote): Future[NoteId] = {
    val note = Note(NoteId.random, newNote.text)
//    client.execute { index into "notes" -> "note" id note doc ObjectSource(newNote) } map { res =>
////      res.getId
//      newId
//    }
    ???
  }

  def get(id: NoteId): Future[Option[Note]] = {
//    client.execute { esGet id id.uuid from "notes" -> "note" } map { res =>
//      None
//    }

//    val req = client2.prepareGet("notes", "note", id.toString)
    val req = Requests.getRequest(index).`type`(typ).id(id.toString)
    future[GetResponse](client.get(req, _)) map { res =>
//      res.
      None
    }
  }

  private def future[A](f: ActionListener[A] => Unit) = {
    val p = Promise[A]()
    f(new ActionListener[A] {
      def onFailure(e: Throwable): Unit = p.tryFailure(e)
      def onResponse(response: A): Unit = p.trySuccess(response)
    })
    p.future
  }
}
