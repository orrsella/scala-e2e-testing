package com.example.memento.common.http

import scala.concurrent.{ExecutionContext, Future}

trait HttpClient {
  def execute(request: HttpRequest)(implicit context: ExecutionContext): Future[HttpResponse]
}
