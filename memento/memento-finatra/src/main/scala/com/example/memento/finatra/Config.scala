package com.example.memento.finatra

import com.typesafe.config.ConfigFactory

object Config {
  private val configPath = "com.example.memento.finatra.config"
  private lazy val conf = ConfigFactory.load().getConfig(configPath)

  lazy val port: Int                        = conf.getInt("port")
  lazy val adminPort: Int                   = conf.getInt("adminPort")
  lazy val yandexTranslateHost: String      = conf.getString("yandexTranslate.host")
  lazy val yandexTranslatePort: Int         = conf.getInt("yandexTranslate.port")
  lazy val yandexTranslateSecure: Boolean   = conf.getBoolean("yandexTranslate.secure")
  lazy val yandexTranslateApiKey: String    = conf.getString("yandexTranslate.apiKey")
  lazy val elasticsearchHost: String        = conf.getString("elasticsearch.host")
  lazy val elasticsearchPort: Int           = conf.getInt("elasticsearch.port")
  lazy val elasticsearchClusterName: String = conf.getString("elasticsearch.clusterName")
}
