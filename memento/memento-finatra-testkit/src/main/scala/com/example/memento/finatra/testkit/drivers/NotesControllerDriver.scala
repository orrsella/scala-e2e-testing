package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.HttpMethod

trait NotesControllerDriver extends ControllerDriver {

  def anAddNoteRequestFor(text: String) = new Request[AddNoteResponse] {
    override val method = HttpMethod.POST
    override val path = "/notes"
    override val body = Some("{ \"text\": \"" + text + "\" }")
    protected val wrapResponse = new AddNoteResponse(_)
  }

  def aGetNoteRequestFor(id: String) = new Request[GetNoteResponse] {
    override val method = HttpMethod.GET
    override val path = s"/notes/$id"
    protected val wrapResponse = new GetNoteResponse(_)
  }

  class AddNoteResponse(response: Response) extends BaseResponse(response) with JsonResponse {
    lazy val noteId = (json \ "id").extract[String]
  }

  class GetNoteResponse(response: Response) extends BaseResponse(response) with JsonResponse {
    lazy val text = (json \ "text").extract[String]
  }
}
