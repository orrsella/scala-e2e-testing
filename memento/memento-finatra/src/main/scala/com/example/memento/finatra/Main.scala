package com.example.memento.finatra

import com.example.memento.core.dal.InMemoryNotesDao
import com.example.memento.finatra.controllers.NotesController
import com.twitter.finatra.FinatraServer
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  System.setProperty("com.twitter.finatra.config.port", ":" + Config.port)
  System.setProperty("com.twitter.finatra.config.adminPort", ":" + Config.adminPort)


//  import com.twitter.logging.config._
//
//  val config = new LoggerConfig {
//    node = ""
//    level = Logger.ALL
//    handlers = new FileHandlerConfig {
//      filename = "/var/log/memento-finatra.log"
//      roll = Policy.SigHup
//    }
//  }
//  config()

  val notesDao = new InMemoryNotesDao()

  val server = new FinatraServer
  server.register(new NotesController(notesDao))
  server.start()
}
