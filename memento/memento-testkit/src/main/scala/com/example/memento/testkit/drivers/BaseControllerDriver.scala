package com.example.memento.testkit.drivers

import com.example.memento.common.http.HttpMethod

trait BaseControllerDriver {

  def aRequest = ErrorRequest()

  case class ErrorRequest(
      method: HttpMethod = HttpMethod.GET,
      path: String = "",
      params: Map[String, String] = Map(),
      headers: Map[String, String] = Map(),
      body: Option[String] = None)
    extends Request[ErrorResponse] {

    protected def buildResponse(response: Response): ErrorResponse = new ErrorResponse(response)

    def withMethod(method: HttpMethod) = copy(method = method)
    def withPath(path: String) = copy(path = path)
    def withParams(params: Map[String, String]) = copy(params = params)
    def withHeaders(headers: Map[String, String]) = copy(headers = headers)
    def withBody(body: Option[String]) = copy(body = body)
  }

  class ErrorResponse(response: Response) extends BaseResponse(response) with JsonResponse {
    lazy val bodyStatus = (json \ "status").extract[Int]
    lazy val message = (json \ "message").extract[String]
  }
}
