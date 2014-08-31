package com.example.memento.finatra.exceptions

import com.example.memento.core.exceptions.ExceptionType._
import org.specs2.mutable.Specification

class ExceptionTypeToHttpStatusMapperTest extends Specification {

  "Exception type to http status mapper" should {
    "map all types to their corresponding Int value" in {
      val mapper = new ExceptionTypeToHttpStatusMapper
      mapper.map(BadRequest) must_== 400
      mapper.map(Unauthorized) must_== 401
      mapper.map(NotFound) must_== 404
      mapper.map(ServerError) must_== 500
      mapper.map(ServiceUnavailable) must_== 503
    }
  }
}
