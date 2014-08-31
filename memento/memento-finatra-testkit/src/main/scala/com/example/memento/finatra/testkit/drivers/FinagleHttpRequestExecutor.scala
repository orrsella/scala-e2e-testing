package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.HttpRequest
import com.example.memento.core.http.finagle.FinagleHttpClient
import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration._
import scala.language.postfixOps

trait FinagleHttpRequestExecutor {
  private val host = "localhost"
  private val port = 8080
  private val client = new FinagleHttpClient(host, port)
  val timeout = 2000 millis

  implicit def executor(implicit context: ExecutionContext): RequestExecutor = { request =>
    val httpRequest = HttpRequest(request.method, request.path, request.params, request.headers, request.body)
    val httpResponse = Await.result(client.execute(httpRequest), timeout)

    new Response {
      val status = httpResponse.status
      val body = httpResponse.body
      val headers = httpResponse.headers
    }
  }
}
