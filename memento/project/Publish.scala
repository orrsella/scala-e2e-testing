import sbt._
import sbt.Keys._

object Publish {

  lazy val noop = Seq(
    publish       := (),
    publishLocal  := ()
  )

  lazy val settings = Seq(
    crossPaths        :=  false,
    publishMavenStyle :=  false
  )
}
