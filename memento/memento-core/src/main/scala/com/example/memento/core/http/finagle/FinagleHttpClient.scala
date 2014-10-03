package com.example.memento.core.http.finagle

import com.example.memento.core.concurrent.Implicits._
import com.example.memento.core.http.{HttpClient, HttpRequest, HttpResponse}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.Service
import com.twitter.logging.LoggerFactory
import java.net.InetSocketAddress
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.HttpHeaders.Names._
import org.jboss.netty.handler.codec.http.{DefaultHttpRequest, HttpMethod, HttpVersion}
import org.jboss.netty.handler.codec.{http => netty}
import org.jboss.netty.util.CharsetUtil
import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

class FinagleHttpClient(host: String, port: Int, secure: Boolean = false) extends HttpClient {

  FinagleHttpClient.disableTwitterLogging
  private val utf8 = CharsetUtil.UTF_8

  private val client: Service[netty.HttpRequest, netty.HttpResponse] = {
    var builder = ClientBuilder()
      .codec(Http())
      .hosts(new InetSocketAddress(host, port))
      .hostConnectionLimit(1)

    if (secure) {
      builder = builder.tls(host)
    }

    builder.build()
  }

  override def execute(request: HttpRequest)(implicit context: ExecutionContext): Future[HttpResponse] = {
    val method = new HttpMethod(request.method.toString)
    val req = new DefaultHttpRequest(HttpVersion.HTTP_1_1, method, request.path)

    request.headers.map(header => req.headers.add(header._1, header._2))
    addHeaderIfNotExists(req, HOST, host)

    if (request.body.isDefined) setContent(req, request.body.get)
    else if (request.params.nonEmpty) setParams(req, request.params)

    val fut = client(req).map { res =>
      val status = res.getStatus.getCode
      val body = res.getContent.toString(utf8)
      val headers = res.headers.asScala.map(h => h.getKey -> h.getValue).toMap
      HttpResponse(status, body, headers)
    }

    fut
  }

  private def setContent(req: netty.HttpRequest, content: String) = {
    val buffer = ChannelBuffers.copiedBuffer(content, utf8)
    req.setContent(buffer)
    req.headers.add(CONTENT_LENGTH, buffer.readableBytes().toString)
  }

  private def setParams(req: netty.HttpRequest, params: Map[String, String]) = {
    val str = params.map(pair => s"${pair._1}=${pair._2}").mkString("&")
    setContent(req, str)
    addHeaderIfNotExists(req, CONTENT_TYPE, "application/x-www-form-urlencoded")
  }

  private def addHeaderIfNotExists(req: netty.HttpRequest, name: String, value: String) = {
    if (!req.headers.contains(name) || req.headers.contains(name.toLowerCase)) {
      req.headers.add(name, value)
    }
  }
}

object FinagleHttpClient {
  private lazy val disableTwitterLogging: Unit = LoggerFactory().apply()
}
