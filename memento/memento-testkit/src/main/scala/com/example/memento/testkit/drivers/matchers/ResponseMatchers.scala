package com.example.memento.testkit.drivers.matchers

import com.example.memento.testkit.drivers.ControllerDriver
import org.specs2.matcher.{Matcher, Matchers}

trait ResponseMatchers extends Matchers {
   implicit def intToIntMatcher(t: Int): Matcher[Int] = beEqualTo(t)
   implicit def stringToStringMatcher(t: String): Matcher[String] = beEqualTo(t)

   def haveStatus(status: Matcher[Int]): Matcher[ControllerDriver#Response] = ((_: ControllerDriver#Response).status) ^^ status
   def haveBody(body: Matcher[String]): Matcher[ControllerDriver#Response] = ((_: ControllerDriver#Response).body) ^^ body
   def haveHeader(name: String, value: String): Matcher[ControllerDriver#Response] = ((_: ControllerDriver#Response).headers) ^^ havePair(name -> value)

   def beOk = haveStatus(200)
   def beBadRequest = haveStatus(400)
   def beNotFound = haveStatus(404)
   def beMovedPermanently = haveStatus(301)
 }
