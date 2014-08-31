package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.HttpMethod

trait ControllerDriver {

  def aRequest = EmptyRequest()

  case class EmptyRequest(
      method: HttpMethod = HttpMethod.GET,
      path: String = "/",
      params: Map[String, String] = Map(),
      headers: Map[String, String] = Map(),
      body: Option[String] = None)
    extends Request[BaseResponse] {

    override protected def buildResponse(response: Response): BaseResponse = new BaseResponse(response) {}

    def withMethod(method: HttpMethod) = copy(method = method)
    def withPath(path: String) = copy(path = path)
    def withParams(params: Map[String, String]) = copy(params = params)
    def withHeaders(headers: Map[String, String]) = copy(headers = headers)
    def withBody(body: Option[String]) = copy(body = body)
  }
}
