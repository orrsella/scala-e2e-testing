package com.example.memento.finatra.controllers

import com.example.memento.core.Logging
import com.example.memento.core.exceptions.BaseRuntimeException
import com.example.memento.finatra.exceptions.ExceptionTypeToHttpStatusMapper
import com.example.memento.finatra.responses.ErrorResponse
import com.twitter.finatra.{Controller, Request}
import com.twitter.logging.LoggerFactory

abstract class BaseController extends Controller with Logging {

  import BaseController._
  disableTwitterLogging
  val mapper = new ExceptionTypeToHttpStatusMapper

  error { req: Request =>
    val response = req.error match {
      case None =>
        logger.error("Unknown error during request")
        DefaultError
      case Some(e: Throwable) =>
        logger.error("Error during request: ", e)
        e match {
          case BaseRuntimeException(exceptionType) => ErrorResponse(mapper.map(exceptionType), e.getMessage)
          case _ => ErrorResponse(DefaultErrorStatus, e.getMessage)
        }
    }

    render.status(response.status).json(response).toFuture
  }

  notFound { req =>
    render.status(NotFoundError.status).json(NotFoundError).toFuture
  }
}

object BaseController {
  private lazy val disableTwitterLogging: Unit = LoggerFactory().apply()
  private val DefaultErrorStatus = 500
  private val DefaultError = ErrorResponse(DefaultErrorStatus, "Internal Server Error")
  private val NotFoundError = ErrorResponse(404, "Not Found")
}
