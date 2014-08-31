package com.example.memento.core

import org.slf4j.LoggerFactory

trait Logging {
  lazy val logger = new Logger
}

class Logger {
  private lazy val logger = LoggerFactory.getLogger(this.getClass.getName.replace("$", ""))

  def trace(msg: Any): Unit = logger.trace(msg.toString)
  def trace(msg: String): Unit = logger.trace(msg)

  def debug(msg: Any): Unit = logger.debug(msg.toString)
  def debug(msg: String): Unit = logger.debug(msg)

  def info(msg: Any): Unit = logger.info(msg.toString)
  def info(msg: String): Unit = logger.info(msg)

  def warn(msg: Any): Unit = logger.warn(msg.toString)
  def warn(msg: String): Unit = logger.info(msg)
  def warn(msg: Any, e: Throwable): Unit = logger.warn(msg.toString, e)
  def warn(msg: String, e: Throwable): Unit = logger.info(msg, e)

  def error(msg: Any): Unit = logger.error(msg.toString)
  def error(msg: String): Unit = logger.error(msg)
  def error(msg: Any, e: Throwable): Unit = logger.error(msg.toString, e)
  def error(msg: String, e: Throwable): Unit = logger.error(msg, e)
}
