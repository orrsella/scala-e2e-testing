package com.example.memento.finatra

import com.typesafe.config.ConfigFactory

object Config {
  private val configPath = "com.example.memento.finatra.config"
  private lazy val conf = ConfigFactory.load().getConfig(configPath)

  val port: Int = conf.getInt("port")
  val adminPort: Int = conf.getInt("adminPort")
}
