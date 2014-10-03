package com.example.memento.core.translation.yandex

import com.example.memento.core.exceptions.TranslationException
import com.example.memento.core.http.{HttpClient, HttpMethod, HttpRequest}
import com.example.memento.core.translation.Translator
import scala.concurrent.{ExecutionContext, Future}

class YandexTranslator(httpClient: HttpClient, apiKey: String)(implicit context: ExecutionContext) extends Translator {

  override def translate(text: String, lang: String): Future[String] = {
    val params = Map("key" -> apiKey, "lang" -> lang, "text" -> text)
    val request = HttpRequest(HttpMethod.POST, "/api/v1.5/tr.json/translate", params)

    httpClient.execute(request) map { res =>
      YandexTranslatorResult(res) match {
        case YandexTranslatorSuccessResult(_, _, texts: Seq[String]) => texts.head
        case YandexTranslatorFailureResult(code, message) => throw new TranslationException(message)
      }
    }
  }
}
