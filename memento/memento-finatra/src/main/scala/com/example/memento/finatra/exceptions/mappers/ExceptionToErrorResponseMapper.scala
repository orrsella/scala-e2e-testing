package com.example.memento.finatra.exceptions.mappers

import com.example.memento.core.exceptions.BaseRuntimeException
import com.example.memento.finatra.responses.ErrorResponse

class ExceptionToErrorResponseMapper {

  private val mapper = new ExceptionTypeToHttpStatusMapper
  private val defaultStatus = 500
  private val defaultMessage = "Internal Server Error"

  def apply(throwable: Option[Throwable]): ErrorResponse = throwable match {
    case None => ErrorResponse(defaultStatus, defaultMessage)
    case Some(t) => t match {
      case BaseRuntimeException(exceptionType) => ErrorResponse(mapper(exceptionType), messageOr(t, "Unknown Error"))
      case _ => ErrorResponse(defaultStatus, messageOr(t, defaultMessage))
    }
  }

  private def messageOr(t: Throwable, default: => String): String = {
    Option(t.getMessage).getOrElse(default)
  }
}
