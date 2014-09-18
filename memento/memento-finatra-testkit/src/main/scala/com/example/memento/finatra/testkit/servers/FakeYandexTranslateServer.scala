package com.example.memento.finatra.testkit.servers

import java.nio.charset.Charset

import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._
import scala.collection.mutable

class FakeYandexTranslateServer extends HttpServer(9921) {

  val json = "{\n   \"code\": 200,\n   \"lang\": \"en-ru\",\n   \"text\": [\n      \"Быть или не быть?\",\n      \"Вот в чем вопрос.\"\n    ]\n}"

  override protected def onRequest(request: HttpRequest): HttpResponse = {
    val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
    response.setContent(ChannelBuffers.copiedBuffer(new mutable.StringBuilder("Hello"), Charset.forName("UTF-8")))
    response
  }
}
