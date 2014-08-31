package com.example.memento.finatra.exceptions

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}

class InvalidRequestBodyException(cause: Throwable)
  extends BaseRuntimeException(ExceptionType.BadRequest, "Failed deserializing request body", cause)
