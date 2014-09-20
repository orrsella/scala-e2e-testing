package com.example.memento.core.translation.yandex

import com.example.memento.common.http.finagle.FinagleHttpClient
import org.specs2.mutable.Specification

class YandexTranslatorIntegrationTest extends Specification {

//  private val fakeYandexTranslate = new FakeYandex

  //  step {
  //    server.start()
  //    server.client.admin.indices.prepareCreate("notes").execute.actionGet()
  ////    server.client.admin.indices.preparePutMapping("notes").setType("note").setSource("{\"note\":{}}").execute.actionGet()
  //  }

  "Yandex translator" should {
    "translate a string" in {
      val apiKey = "trnsl.1.1.20140920T205822Z.095fb3fc50a4a329.a26f12c4c4ccce89b2125dbb99cb069f67fdb0f4"
      val translator = new YandexTranslator(new FinagleHttpClient("translate.yandex.net", 443, secure = true), apiKey)

      val translated = translator.translate("Hello", "es")
      translated must beEqualTo("Hola").await
    }
  }
}
