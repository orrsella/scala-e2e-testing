package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.{HttpRequest, HttpMethod}
import com.example.memento.core.http.dispatch.FinagleHttpClient
import com.twitter.finatra.test.SpecHelper
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

trait ControllerDriver { this: ControllerDriverRequestExecutor =>

  trait Response {
    def status: Int
    def body: String
    def headers: Map[String, String]
  }

  trait Request[R <: Response] {
    def method: HttpMethod
    def path: String
    def params: Map[String, String] = Map()
    def headers: Map[String, String] = Map()
    def body: Option[String] = None

    protected def wrapResponse: Response => R

    def execute(): R = {
      val base = executeRequest(this)
      wrapResponse(base)
    }
//    def execute(): R = {
//      val uri = path
//      val headersWithHost = headers + ("Host" -> domain) // required because nginx listens for a specific server_name
//      val httpRequest = HttpRequest(method, uri, headers = headersWithHost, body = body)
//      val httpResponse = Await.result(client.execute(httpRequest), timeout)
//
//      val base = new Response {
//        val status = httpResponse.status
//        val body = httpResponse.body
//        val headers = httpResponse.headers
//      }
//
//      wrapResponse(base)
//    }
  }

  abstract class BaseResponse(response: Response) extends Response {
    val status = response.status
    val body = response.body
    val headers = response.headers
  }

  trait JsonResponse { this: Response =>
    protected implicit val formats = DefaultFormats
    protected lazy val json = parse(body)
  }

  trait ControllerDriverRequestExecutor {
    def executeRequest[R](request: Request[R]): Response
  }

  trait FinagleHttpRequestExecutor extends ControllerDriverRequestExecutor {
    private val host = "localhost"
    private val port = 8080
//    private val domain = "example.com"
    private val client = new FinagleHttpClient(host, port)
    protected val timeout = 2000 millis

    override def executeRequest[R](request: Request[R]): Response = {
//      val headersWithHost = headers + ("Host" -> domain) // required because nginx listens for a specific server_name
      val httpRequest = HttpRequest(request.method, request.path, request.params, request.headers, request.body)
      val httpResponse = Await.result(client.execute(httpRequest), timeout)

      new Response {
        val status = httpResponse.status
        val body = httpResponse.body
        val headers = httpResponse.headers
      }
    }
  }

  trait FinatraMockRequestExecutor extends ControllerDriverRequestExecutor with SpecHelper {
    override def executeRequest[R](request: Request[R]): Response = {
      val (path, params, headers, body) = (request.path, request.params, request.headers, request.body.getOrElse(""))
      request.method match {
        case HttpMethod.GET     => get(path, params, headers)
        case HttpMethod.POST    => post(path, params, headers, body)
        case HttpMethod.PUT     => put(path, params, headers, body)
        case HttpMethod.DELETE  => delete(path, params, headers)
        case HttpMethod.OPTIONS => options(path, params, headers, body)
        case HttpMethod.HEAD    => head(path, params, headers)
        case HttpMethod.PATCH   => patch(path, params, headers)
        case m => new Exception("Unsupported http method " + m.toString)
      }

      new Response {
        val status = response.status
        val body = response.body
        val headers = response.getHeaders
      }
    }
  }
}

