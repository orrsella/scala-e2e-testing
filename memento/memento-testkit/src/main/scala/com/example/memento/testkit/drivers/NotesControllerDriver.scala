package com.example.memento.testkit.drivers

import com.example.memento.core.http.HttpMethod
import org.json4s._
import org.json4s.native.JsonMethods._

trait NotesControllerDriver extends ControllerDriver {

  private implicit val formats = DefaultFormats

  def anAddNoteRequestFor(text: String): AddNoteRequest = new AddNoteRequest(text)

  case class AddNoteRequest(text: String) extends Request[AddNoteResponse] {
    val method = HttpMethod.POST
    val path = "/notes"
    val body = Some("{ \"text\": \"" + text + "\" }")
    val headers = Map[String, String]()
    def execute() = new AddNoteResponse(doExecute(this))
  }

  class AddNoteResponse(response: Response) extends Response {
    val status = response.status
    val body = response.body
    val headers = response.headers

    private lazy val jsonBody = parse(body)
    lazy val noteId: String = (jsonBody \ "id").extract[String]
  }
}
