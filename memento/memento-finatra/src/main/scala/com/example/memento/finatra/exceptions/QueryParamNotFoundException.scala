package com.example.memento.finatra.exceptions

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}

class QueryParamNotFoundException(name: String)
  extends BaseRuntimeException(ExceptionType.BadRequest, s"Required query parameter $name not found")
