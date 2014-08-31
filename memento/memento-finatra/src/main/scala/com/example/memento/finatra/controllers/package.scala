package com.example.memento.finatra

import com.example.memento.finatra.exceptions.{InvalidRequestBodyException, RouteParamNotFoundException}
import com.twitter.finatra.Request
import org.jboss.netty.util.CharsetUtil
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods._

package object controllers {

  private implicit val formats = DefaultFormats

  implicit class RichRequest(val request: Request) extends AnyVal {
    def getRouteParam(name: String): String = request.routeParams.getOrElse(name, throw new RouteParamNotFoundException(name))

    def bodyAs[T](implicit m: Manifest[T]): T = {
      try {
        val body = request.content.toString(CharsetUtil.UTF_8)
        val json = parse(body)
        json.extract[T]
      } catch {
        case e: Exception => throw new InvalidRequestBodyException(e)
      }
    }
  }
}
