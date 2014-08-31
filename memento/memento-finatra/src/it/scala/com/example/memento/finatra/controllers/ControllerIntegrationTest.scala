package com.example.memento.finatra.controllers

import com.example.memento.finatra.testkit.drivers.FinatraMockRequestExecutor
import com.example.memento.finatra.testkit.matchers.ResponseMatchers
import com.twitter.finatra.{Controller, FinatraServer}

trait ControllerIntegrationTest extends FinatraMockRequestExecutor with ResponseMatchers {

  def controller: Controller

  lazy val server = {
    val s = new FinatraServer
    s.register(controller)
    s
  }
}
