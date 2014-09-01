package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.HttpMethod

trait NotesControllerDriver {

  def anAddNoteRequest = AddNoteRequest()
  def aGetNoteRequest = GetNoteRequest()

  case class AddNoteRequest(text: String = "Lorem ipsum") extends Request[AddNoteResponse] {
    val method = HttpMethod.POST
    val path = "/notes"
    val params: Map[String, String] = Map()
    val headers: Map[String, String] = Map()
    val body: Option[String] = Some("{ \"text\": \"" + text + "\" }")

    protected def buildResponse(response: Response): AddNoteResponse = new AddNoteResponse(response)
    def withText(text: String) = copy(text = text)
  }

  case class GetNoteRequest(id: String = "123") extends Request[GetNoteResponse] {
    val method = HttpMethod.GET
    val path = s"/notes/$id"
    val params: Map[String, String] = Map()
    val headers: Map[String, String] = Map()
    val body: Option[String] = None

    protected def buildResponse(response: Response): GetNoteResponse = new GetNoteResponse(response)
    def withId(id: String) = copy(id = id)
  }

  class AddNoteResponse(response: Response) extends BaseResponse(response) with JsonResponse {
    lazy val noteId = (json \ "noteId").extract[String]
  }

  class GetNoteResponse(response: Response) extends BaseResponse(response) with JsonResponse {
    lazy val text = (json \ "text").extract[String]
  }
}
