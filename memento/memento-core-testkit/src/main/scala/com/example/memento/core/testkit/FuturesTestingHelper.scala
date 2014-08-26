package com.example.memento.core.testkit

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.implicitConversions

trait FuturesTestingHelper {
  val timeout = 100.milli
//  implicit val executionContext: ExecutionContext = ExecutionContext.global
  implicit def await[T](future: Future[T]): T = Await.result(future, timeout)
}
