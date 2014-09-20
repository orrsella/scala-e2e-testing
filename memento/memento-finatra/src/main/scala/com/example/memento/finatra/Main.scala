package com.example.memento.finatra

import com.example.memento.core.dal.InMemoryNotesDao
import com.example.memento.common.http.finagle.FinagleHttpClient
import com.example.memento.core.translation.yandex.YandexTranslator
import com.example.memento.finatra.controllers.NotesController
import com.twitter.finatra.FinatraServer
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  System.setProperty("com.twitter.finatra.config.port", ":" + Config.port)
  System.setProperty("com.twitter.finatra.config.adminPort", ":" + Config.adminPort)

  val notesDao = new InMemoryNotesDao()
//  val translator = new InMemoryTranslator()
  val translator = new YandexTranslator(new FinagleHttpClient("translate.yandex.net", 443, secure = true), "trnsl.1.1.20140920T205822Z.095fb3fc50a4a329.a26f12c4c4ccce89b2125dbb99cb069f67fdb0f4")

  val server = new FinatraServer
  server.register(new NotesController(notesDao, translator))
  server.start()
}
