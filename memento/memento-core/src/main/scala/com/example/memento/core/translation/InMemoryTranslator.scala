package com.example.memento.core.translation

import scala.concurrent.{ExecutionContext, Future}

class InMemoryTranslator(implicit context: ExecutionContext) extends Translator {
  private val translations = Map("Good morning" -> "Buenos días")

  override def translate(text: String, lang: String): Future[String] = Future.successful(translations.getOrElse(text, text))
}
