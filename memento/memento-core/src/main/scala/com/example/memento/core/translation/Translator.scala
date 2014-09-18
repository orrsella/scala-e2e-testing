package com.example.memento.core.translation

import scala.concurrent.Future

trait Translator {
  def translate(text: String, lang: String): Future[String]
}
