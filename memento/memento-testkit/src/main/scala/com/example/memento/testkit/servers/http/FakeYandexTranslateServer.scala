package com.example.memento.testkit.servers.http

import org.jboss.netty.handler.codec.http._

class FakeYandexTranslateServer(port: Int) extends SimpleHttpServer(port) {

  private val json =
    """{
      |"code": 200,
      |"lang": "en-es",
      |"text": ["Buenos días"]
      |}
    """.stripMargin

  override protected def onSimpleRequest(request: HttpRequest): String = json
}
