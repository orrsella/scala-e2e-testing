package com.example.memento.finatra

import com.example.memento.core.Logging
import com.example.memento.core.dal.InMemoryNotesDao
import com.example.memento.core.http.finagle.FinagleHttpClient
import com.example.memento.core.translation.yandex.YandexTranslator
import com.example.memento.finatra.controllers.NotesController
import com.twitter.finatra.FinatraServer
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App with Logging {

  System.setProperty("com.twitter.finatra.config.port", ":" + Config.port)
  System.setProperty("com.twitter.finatra.config.adminPort", ":" + Config.adminPort)

  try {
    val notesDao = new InMemoryNotesDao()
    val client = new FinagleHttpClient(Config.yandexTranslateHost, Config.yandexTranslatePort, Config.yandexTranslateSecure)
    val translator = new YandexTranslator(client, Config.yandexTranslateApiKey)

    val server = new FinatraServer
    server.register(new NotesController(notesDao, translator))
    server.start()
  } catch {
    case e: Exception => logger.error("Error starting server", e)
  }
}
