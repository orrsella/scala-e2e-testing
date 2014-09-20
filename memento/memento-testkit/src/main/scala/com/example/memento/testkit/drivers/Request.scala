package com.example.memento.testkit.drivers

import com.example.memento.common.http.HttpMethod

trait Request[R] {
  def method: HttpMethod
  def path: String
  def params: Map[String, String]
  def headers: Map[String, String]
  def body: Option[String]

  protected def buildResponse(response: Response): R

  def execute()(implicit executor: RequestExecutor): R = buildResponse(executor(this))
}
