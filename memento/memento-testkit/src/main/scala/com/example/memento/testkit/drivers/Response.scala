package com.example.memento.testkit.drivers

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._

trait Response {
  def status: Int
  def body: String
  def headers: Map[String, String]
}

abstract class BaseResponse(response: Response) extends Response {
  val status = response.status
  val body = response.body
  val headers = response.headers
}

trait JsonResponse { this: Response =>
  protected implicit val formats = DefaultFormats
  protected lazy val json = parse(body)
}
