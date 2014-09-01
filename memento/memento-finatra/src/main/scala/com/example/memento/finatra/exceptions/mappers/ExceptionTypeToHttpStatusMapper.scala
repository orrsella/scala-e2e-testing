package com.example.memento.finatra.exceptions.mappers

import com.example.memento.core.exceptions.ExceptionType
import com.example.memento.core.exceptions.ExceptionType._

class ExceptionTypeToHttpStatusMapper {
  def apply(exceptionType: ExceptionType): Int = exceptionType match {
    case BadRequest         => 400
    case Unauthorized       => 401
    case NotFound           => 404
    case ServerError        => 500
    case ServiceUnavailable => 503
  }
}
