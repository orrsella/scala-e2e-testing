package com.example.memento.core.translation.yandex

import com.example.memento.core.http.finagle.FinagleHttpClient
import com.example.memento.testkit.servers.http.FakeYandexTranslateServer
import org.specs2.mutable.Specification

class YandexTranslatorIntegrationTest extends Specification {

  private val port = 9921
  private val server = new FakeYandexTranslateServer(port)

  step {
    server.start()
  }

  "Yandex translator" should {
    "translate a string" in {
      val translator = new YandexTranslator(new FinagleHttpClient("localhost", port), "API_KEY")
      val text = translator.translate("Good morning", "es")
      text must beEqualTo("Buenos días").await
    }
  }

  step {
    server.stop()
  }
}
