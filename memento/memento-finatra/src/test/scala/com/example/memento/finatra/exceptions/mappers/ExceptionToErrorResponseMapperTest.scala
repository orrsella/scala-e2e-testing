package com.example.memento.finatra.exceptions.mappers

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class ExceptionToErrorResponseMapperTest extends Specification {

  trait Context extends Scope {
    val mapper = new ExceptionToErrorResponseMapper
  }

  "Exception to error response mapper" should {
    "map None to default error response" in new Context {
      val response = mapper(None)
      response.status must_== 500
      response.message must_== "Internal Server Error"
    }

    "map an exception to default error response" in new Context {
      val ex = new Exception("Error msg")
      val response = mapper(Some(ex))
      response.status must_== 500
      response.message must_== "Error msg"
    }

    "map an exception without a message to default error response" in new Context {
      val ex = new Exception()
      val response = mapper(Some(ex))
      response.status must_== 500
      response.message must_== "Internal Server Error"
    }

    "map a BaseRuntimeException to it's corresponding error response" in new Context {
      val ex = new BaseRuntimeException(exceptionType = ExceptionType.NotFound, "Some err msg") { }
      val response = mapper(Some(ex))
      response.status must_== 404
      response.message must_== "Some err msg"
    }

    "map a BaseRuntimeException with no message to error response with default message" in new Context {
      val ex = new BaseRuntimeException(exceptionType = ExceptionType.NotFound) { }
      val response = mapper(Some(ex))
      response.status must_== 404
      response.message must_== "Unknown Error"
    }
  }
}
