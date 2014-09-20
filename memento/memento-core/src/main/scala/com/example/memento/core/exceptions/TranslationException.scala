package com.example.memento.core.exceptions

class TranslationException(msg: String)
  extends BaseRuntimeException(ExceptionType.ServerError, msg)
