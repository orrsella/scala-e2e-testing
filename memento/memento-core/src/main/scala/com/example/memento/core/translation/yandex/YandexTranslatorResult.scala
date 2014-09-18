package com.example.memento.core.translation.yandex

import com.example.memento.core.exceptions.TranslationException
import com.example.memento.core.http.HttpResponse
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._

sealed trait YandexTranslatorResult
case class YandexTranslatorFailureResult(code: Int, message: String) extends YandexTranslatorResult
case class YandexTranslatorSuccessResult(code: Int, lang: String, text: Seq[String]) extends YandexTranslatorResult

object YandexTranslatorResult {
  implicit val formats = DefaultFormats

  def apply(response: HttpResponse): YandexTranslatorResult = response match {
    case HttpResponse(200, body, _) =>
      try {
        parse(body).extract[YandexTranslatorSuccessResult]
      } catch {
        case e: Exception => throw new TranslationException(s"Error parsing translation successful response. Exception=[${e.getMessage}], Response=[$body]")
      }
    case HttpResponse(status, body, _) =>
      try {
        parse(body).extract[YandexTranslatorFailureResult]
      } catch {
        case e: Exception => throw new TranslationException(s"Error parsing translation failure response. Exception=[${e.getMessage}], Response=[$body]")
      }
    case _ => throw new TranslationException("Unknown translation error")
  }
}
