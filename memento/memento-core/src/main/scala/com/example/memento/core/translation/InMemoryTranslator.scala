package com.example.memento.core.translation

import scala.concurrent.{ExecutionContext, Future}

class InMemoryTranslator(implicit context: ExecutionContext) extends Translator {
  override def translate(text: String, lang: String): Future[String] = Future.successful(text)
}
