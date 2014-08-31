package com.example.memento.core.concurrent

import com.twitter.util.{Future => TwitterFuture, Promise => TwitterPromise, Throw, Return}
import scala.concurrent.{ExecutionContext, Promise, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success}

object Implicits {

  implicit def twitterFutureToScalaFuture[T](future: TwitterFuture[T])(implicit context: ExecutionContext): Future[T] = {
    val p = Promise[T]()
    future.respond {
      case Return(t) => p.success(t)
      case Throw(e)  => p.failure(e)
    }
    p.future
  }

  implicit def scalaFutureToTwitterFuture[T](future: Future[T])(implicit context: ExecutionContext): TwitterFuture[T] = {
    val p = new TwitterPromise[T]()
    future.onComplete {
      case Success(t) => p.setValue(t)
      case Failure(e) => p.setException(e)
    }
    p
  }
}
