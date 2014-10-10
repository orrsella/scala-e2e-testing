package com.example.memento.core.dal

import com.example.memento.core.exceptions.FailedIndexingException
import com.example.memento.core.json.Json4sMapper
import com.example.memento.core.model.Note
import java.util.UUID
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{Client, Requests}
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import scala.concurrent.{ExecutionContext, Future, Promise}

class ElasticsearchNotesDao(client: Client)(implicit context: ExecutionContext) extends NotesDao {
  private val index = "notes"
  private val typ = "note"
  private val mapper = new Json4sMapper

  def add(text: String): Future[UUID] = {
    val note = Note(UUID.randomUUID(), text)
    val req = Requests.indexRequest(index).`type`(typ).id(note.id.toString).source(mapper.encode(note))

    future[IndexResponse](client.index(req, _)) map { res =>
      if (res.isCreated) note.id
      else throw new FailedIndexingException(s"Index=$index")
    }
  }

  def get(id: UUID): Future[Option[Note]] = {
    val req = Requests.getRequest(index).`type`(typ).id(id.toString)

    future[GetResponse](client.get(req, _)) map { res =>
      if (res.isExists) Some(mapper.decode[Note](res.getSourceAsString))
      else None
    }
  }

  private def future[A](f: ActionListener[A] => Unit) = {
    val p = Promise[A]()
    f(new ActionListener[A] {
      def onFailure(e: Throwable) = p.tryFailure(e)
      def onResponse(response: A) = p.trySuccess(response)
    })
    p.future
  }
}

object ElasticsearchNotesDao {
  def apply(host: String, port: Int, clusterName: String)(implicit context: ExecutionContext): ElasticsearchNotesDao = {
    val settings = ImmutableSettings.settingsBuilder
      .put("cluster.name", clusterName)
      .put("client.transport.sniff", true)
      .build

    val client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port))
    new ElasticsearchNotesDao(client)
  }
}
