package com.example.memento.finatra.exceptions

import com.example.memento.core.exceptions.{ExceptionType, BaseRuntimeException}
import com.example.memento.core.model.NoteId

case class NoteNotFoundException(id: NoteId)
  extends BaseRuntimeException(ExceptionType.NotFound, s"Note with id '${id.uuid}' not found")
