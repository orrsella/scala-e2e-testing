package com.example.memento.finatra.exceptions.mappers

import com.example.memento.core.exceptions.ExceptionType._
import org.specs2.mutable.Specification

class ExceptionTypeToHttpStatusMapperTest extends Specification {

  "Exception type to http status mapper" should {
    "map all types to their corresponding Int value" in {
      val mapper = new ExceptionTypeToHttpStatusMapper

      mapper(BadRequest) must_== 400
      mapper(Unauthorized) must_== 401
      mapper(NotFound) must_== 404
      mapper(ServerError) must_== 500
      mapper(ServiceUnavailable) must_== 503
    }
  }
}
