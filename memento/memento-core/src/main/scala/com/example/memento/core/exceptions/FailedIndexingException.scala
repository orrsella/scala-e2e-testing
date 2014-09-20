package com.example.memento.core.exceptions

class FailedIndexingException(msg: String)
  extends BaseRuntimeException(ExceptionType.ServerError, s"Failed indexing object: $msg")
