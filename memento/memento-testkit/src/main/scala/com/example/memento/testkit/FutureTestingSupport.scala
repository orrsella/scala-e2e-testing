package com.example.memento.testkit

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions

trait FutureTestingSupport {
  val timeout: Duration = 1000.milli
  implicit def futureToValue[T](f: Future[T]): T = Await.result(f, timeout)
}
