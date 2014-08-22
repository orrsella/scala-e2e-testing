package com.example.memento.testkit.drivers

import com.example.memento.core.http.dispatch.FinagleHttpClient
import com.example.memento.core.http.{HttpMethod, HttpRequest}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

trait ControllerDriver {
  private val host = "localhost"
  private val port = 8080
  private val domain = "example.com"
  private val client = new FinagleHttpClient(host, port)
  protected def timeout = 2000 millis

  trait Response {
    def status: Int
    def body: String
    def headers: Map[String, String]
  }

  trait Request[R <: Response] {
    def method: HttpMethod
    def path: String
    def body: Option[String]
    def headers: Map[String, String]
    def execute(): R

    protected def doExecute(request: Request[R]): Response = {
      val uri = path
      val headersWithHost = headers + ("Host" -> domain) // required because nginx listens for a specific server_name
      val httpRequest = HttpRequest(HttpMethod.GET, uri, headers = headersWithHost, body = body)
      val httpResponse = Await.result(client.execute(httpRequest), timeout)

      new Response {
        override def status: Int = httpResponse.status
        override def body: String = httpResponse.body
        override def headers: Map[String, String] = httpResponse.headers
      }
    }
  }
}
