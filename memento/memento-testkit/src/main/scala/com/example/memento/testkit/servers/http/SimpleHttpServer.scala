package com.example.memento.testkit.servers.http

import java.nio.charset.Charset
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.{HttpRequest, HttpResponse, _}
import scala.collection.mutable

abstract class SimpleHttpServer(port: Int) extends HttpServer(port) {

  final override protected def onRequest(request: HttpRequest): HttpResponse = {
    val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    response.setContent(ChannelBuffers.copiedBuffer(new mutable.StringBuilder(onSimpleRequest(request)), Charset.forName("UTF-8")))
    response
  }

  protected def onSimpleRequest(request: HttpRequest): String
}
