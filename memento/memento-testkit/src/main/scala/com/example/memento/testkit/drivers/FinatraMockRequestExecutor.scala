package com.example.memento.testkit.drivers

import com.example.memento.core.http.HttpMethod
import com.twitter.finatra.FinatraServer
import com.twitter.finatra.test.SpecHelper

trait FinatraMockRequestExecutor extends SpecHelper {

  def server: FinatraServer

  implicit def executor: RequestExecutor = { request =>
    val (path, params, headers, body) = (request.path, request.params, request.headers, request.body.getOrElse(""))

    request.method match {
      case HttpMethod.GET     => get(path, params, headers)
      case HttpMethod.POST    => post(path, params, headers, body)
      case HttpMethod.PUT     => put(path, params, headers, body)
      case HttpMethod.DELETE  => delete(path, params, headers)
      case HttpMethod.OPTIONS => options(path, params, headers, body)
      case HttpMethod.HEAD    => head(path, params, headers)
      case HttpMethod.PATCH   => patch(path, params, headers)
      case m => new Exception("Unsupported http method " + m.toString)
    }

    new Response {
      val status = response.status.getCode
      val body = response.body
      val headers = response.getHeaders.toMap
    }
  }
}
