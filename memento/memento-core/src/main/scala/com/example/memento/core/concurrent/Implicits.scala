package com.example.memento.core.concurrent

import com.twitter.util.{Future => TwitterFuture}
import scala.concurrent.{Promise, Future}
import scala.language.implicitConversions

object Implicits {

  private def twitterFutureToScalaFuture[T](future: TwitterFuture[T]): Future[T] = {
    val p = Promise[T]()

    future.onSuccess { res: T =>
      p.success(res)
    } onFailure { t: Throwable =>
      p.failure(t)
    }

    p.future
  }

  implicit class RichTwitterFuture[T](val future: TwitterFuture[T]) extends AnyVal {
    def toScalaFuture: Future[T] = twitterFutureToScalaFuture(future)
  }
}
