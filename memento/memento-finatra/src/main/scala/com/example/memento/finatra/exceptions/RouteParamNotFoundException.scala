package com.example.memento.finatra.exceptions

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}

class RouteParamNotFoundException(name: String)
  extends BaseRuntimeException(ExceptionType.BadRequest, s"Required route parameter $name not found")
