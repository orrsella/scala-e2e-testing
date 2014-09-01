package com.example.memento.finatra.controllers

import com.example.memento.core.Logging
import com.example.memento.finatra.exceptions.mappers.ExceptionToErrorResponseMapper
import com.example.memento.finatra.responses.ErrorResponse
import com.twitter.finatra.{ResponseBuilder, Controller}
import com.twitter.logging.LoggerFactory
import com.twitter.util.Future

abstract class BaseController extends Controller with Logging {

  BaseController.disableTwitterLogging
  private val mapper = new ExceptionToErrorResponseMapper

  error { req =>
    log(req.error)
    response(mapper(req.error))
  }

  notFound { req =>
    response(ErrorResponse(404, "Not Found"))
  }

  private def response(err: ErrorResponse): Future[ResponseBuilder] = render.status(err.status).json(err).toFuture

  private def log(throwable: Option[Throwable]): Unit = throwable match {
    case Some(t)  => logger.error("Error during request: ", t)
    case None     => logger.error("Unknown error during request")
  }
}

object BaseController {
  private lazy val disableTwitterLogging: Unit = LoggerFactory().apply()
}
