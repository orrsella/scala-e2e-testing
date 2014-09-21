package com.example.memento.finatra

import com.typesafe.config.ConfigFactory

object Config {
  private val configPath = "com.example.memento.finatra.config"
  private lazy val conf = ConfigFactory.load().getConfig(configPath)

  lazy val port: Int                       = conf.getInt("port")
  lazy val adminPort: Int                  = conf.getInt("adminPort")
  lazy val yandexTranslateHost: String     = conf.getString("yandexTranslateHost")
  lazy val yandexTranslatePort: Int        = conf.getInt("yandexTranslatePort")
  lazy val yandexTranslateSecure: Boolean  = conf.getBoolean("yandexTranslateSecure")
  lazy val yandexTranslateApiKey: String   = conf.getString("yandexTranslateApiKey")
}
