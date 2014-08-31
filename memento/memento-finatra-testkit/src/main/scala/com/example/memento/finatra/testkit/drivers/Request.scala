package com.example.memento.finatra.testkit.drivers

import com.example.memento.core.http.HttpMethod

trait Request[R] {
  def method: HttpMethod
  def path: String
  def params: Map[String, String]
  def headers: Map[String, String]
  def body: Option[String]

  protected def buildResponse(response: Response): R

  def execute()(implicit executor: RequestExecutor): R = buildResponse(executor(this))
}
