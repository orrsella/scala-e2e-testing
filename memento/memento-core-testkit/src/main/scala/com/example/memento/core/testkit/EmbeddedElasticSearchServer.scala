package com.example.memento.core.testkit

import java.nio.file.Files
import org.apache.commons.io.FileUtils
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.node.NodeBuilder

class EmbeddedElasticSearchServer {
  private val clusterName = "elasticsearch"
  private val dataDir = Files.createTempDirectory("elasticsearch_data_").toFile
  private val settings = ImmutableSettings.settingsBuilder.put("path.data", dataDir.toString).put("cluster.name", clusterName).build //.put("http.enabled", "false")
  private lazy val node = new NodeBuilder().local(true).settings(settings).build

  def client = node.client()

  def start(): Unit = {
    node.start()
  }

  def stop(): Unit = {
    node.close()

    try {
      FileUtils.forceDelete(dataDir)
    } catch {
      case e: Exception => //error("Error trying to run embedded elastic search server cleanup", e)
    }
  }
}
