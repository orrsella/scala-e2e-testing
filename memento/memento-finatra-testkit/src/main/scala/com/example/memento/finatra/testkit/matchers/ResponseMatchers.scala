package com.example.memento.finatra.testkit.matchers

import com.example.memento.finatra.testkit.drivers.Response
import org.specs2.matcher.{Matcher, Matchers}

trait ResponseMatchers extends Matchers {
  private implicit def intToIntMatcher(t: Int): Matcher[Int] = beEqualTo(t)
  private implicit def stringToStringMatcher(t: String): Matcher[String] = beEqualTo(t)

  def haveStatus(status: Matcher[Int]): Matcher[Response] = ((_: Response).status) ^^ status
  def haveBody(body: Matcher[String]): Matcher[Response] = ((_: Response).body) ^^ body
  def haveHeader(name: String, value: String): Matcher[Response] = ((_: Response).headers) ^^ havePair(name -> value)

  def beOk = haveStatus(200)
  def beBadRequest = haveStatus(400)
  def beNotFound = haveStatus(404)
  def beMovedPermanently = haveStatus(301)
  def beServerError = haveStatus(500)
}
