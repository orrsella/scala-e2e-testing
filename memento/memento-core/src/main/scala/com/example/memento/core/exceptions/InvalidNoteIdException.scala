package com.example.memento.core.exceptions

class InvalidNoteIdException(id: String, cause: Throwable)
  extends BaseRuntimeException(ExceptionType.BadRequest, s"Invalid note id '$id'", cause)
