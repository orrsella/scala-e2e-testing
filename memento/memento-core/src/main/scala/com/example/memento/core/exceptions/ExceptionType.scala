package com.example.memento.core.exceptions

sealed trait ExceptionType

object ExceptionType {
  case object BadRequest extends ExceptionType
  case object Unauthorized extends ExceptionType
  case object NotFound extends ExceptionType
  case object ServerError extends ExceptionType
  case object ServiceUnavailable extends ExceptionType
}

