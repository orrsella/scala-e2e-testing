package com.example.memento.core.dal

import com.example.memento.core.model.Note
import org.specs2.matcher.{Matcher, Matchers}

trait NoteMatchers extends Matchers {

  def haveText(text: String): Matcher[Option[Note]] = haveText(beEqualTo(text))
  def haveText(text: Matcher[String]): Matcher[Option[Note]] = text ^^ ((_: Option[Note]).get.text)
}
