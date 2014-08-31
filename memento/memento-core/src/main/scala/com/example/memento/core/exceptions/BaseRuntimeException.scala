package com.example.memento.core.exceptions

class BaseRuntimeException(
    val exceptionType: ExceptionType,
    message: String,
    cause: Throwable)
  extends RuntimeException(message, cause) {

  def this(exceptionType: ExceptionType) = this(exceptionType, null, null)
  def this(exceptionType: ExceptionType, message: String) = this(exceptionType, message, null)
  def this(exceptionType: ExceptionType, cause: Throwable) = this(exceptionType, null, cause)
}

object BaseRuntimeException {
  def unapply(e: BaseRuntimeException): Option[ExceptionType] = Some(e.exceptionType)
}
