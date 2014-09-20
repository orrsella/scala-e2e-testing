package com.example.memento.finatra.controllers

import com.example.memento.common.http.HttpMethod
import com.example.memento.testkit.drivers.BaseControllerDriver
import com.example.memento.testkit.matchers.ResponseMatchers
import com.twitter.finatra.Controller
import org.specs2.mutable.Specification

class BaseControllerIntegrationTest
  extends Specification
  with ControllerIntegrationTest
  with BaseControllerDriver
  with ResponseMatchers {

  sequential

  val controller: Controller = new BaseController {
    post("/bar") { req =>
      render.plain("Hello from /bar").toFuture
    }

    get("/boom") { req =>
      throw new Exception("Boom!")
    }
  }

  "Base controller" should {
    "return 404 not found for a missing route" in {
      val response = aRequest.withPath("/foo").execute()
      response must beNotFound
      response.bodyStatus must_== 404
      response.message must_== "Not Found"
    }

    "return 404 for a route with wrong http method" in {
      val response = aRequest.withPath("/bar").withMethod(HttpMethod.PUT).execute()
      response must beNotFound
    }

    "return 500 for a route with exception" in {
      val response = aRequest.withPath("/boom").execute()
      response must beServerError
      response.bodyStatus must_== 500
      response.message must_== "Boom!"
    }
  }
}
