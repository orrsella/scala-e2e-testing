package com.example.memento.finatra.exceptions

import java.util.UUID

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}

case class NoteNotFoundException(id: UUID)
  extends BaseRuntimeException(ExceptionType.NotFound, s"Note with id '$id' not found")
