package com.example.memento.core.http.dispatch

import com.example.memento.core.concurrent.Implicits._
import com.example.memento.core.http.{HttpClient, HttpRequest, HttpResponse}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.Service
import java.net.InetSocketAddress
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.{HttpHeaders, DefaultHttpRequest, HttpMethod, HttpVersion}
import org.jboss.netty.handler.codec.{http => netty}
import org.jboss.netty.util.CharsetUtil
import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

class FinagleHttpClient(host: String, port: Int) extends HttpClient {

  private val utf8 = CharsetUtil.UTF_8

  private val client: Service[netty.HttpRequest, netty.HttpResponse] = ClientBuilder()
    .codec(Http())
    .hosts(new InetSocketAddress(host, port))
    .hostConnectionLimit(1)
    .build()

  override def execute(request: HttpRequest)(implicit context: ExecutionContext): Future[HttpResponse] = {
    val method = new HttpMethod(request.method.toString)
    val req = new DefaultHttpRequest(HttpVersion.HTTP_1_1, method, request.path)

    request.headers.map(header => req.headers.add(header._1, header._2))
    request.body.map { body =>
      val buffer = ChannelBuffers.copiedBuffer(body, utf8)
      req.setContent(buffer)
      req.headers.add(HttpHeaders.Names.CONTENT_LENGTH, buffer.readableBytes().toString)
    }

    // TODO: handle request.params

    val future = client(req).map { res =>
      val status = res.getStatus.getCode
      val body = res.getContent.toString(utf8)
      val headers = res.headers.asScala.map(h => h.getKey -> h.getValue).toMap
      HttpResponse(status, body, headers)
    }

    future
  }
}
