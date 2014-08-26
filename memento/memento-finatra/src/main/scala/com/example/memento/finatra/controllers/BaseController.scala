package com.example.memento.finatra.controllers

import com.example.memento.core.Logging
import com.twitter.finatra.{Controller, Request}
import com.twitter.logging.LoggerFactory
import org.jboss.netty.util.CharsetUtil
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._

abstract class BaseController extends Controller with Logging {

  BaseController.disableTwitterLogging

  private implicit val formats = DefaultFormats

  error { req: Request =>
    val ex = req.error
    ex match {
      case Some(e: Throwable) => logger.error("Error during request: ", e)
      case None => logger.error("Unknown error during request")
    }

    val cause = ex.map(_.getMessage)
    val response = ErrorResponse(500, "Internal Server Error", cause)
    render.status(500).json(response).toFuture
  }

  case class ErrorResponse(status: Int, message: String, cause: Option[String])

  def parseBody[T](req: Request)(implicit m: Manifest[T]): T = {
    val body = req.request.content.toString(CharsetUtil.UTF_8)
    val json = parse(body)
    json.extract[T]
  }
}

object BaseController {
  lazy val disableTwitterLogging: Unit = LoggerFactory().apply()
}
