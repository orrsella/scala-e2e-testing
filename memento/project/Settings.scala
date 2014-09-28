import sbt._
import sbt.Keys._
import scala.collection.immutable.Seq

object Settings {

  import BuildKeys._

  private lazy val general = Seq(
    version           <<= version in ThisBuild,
    scalaVersion      :=  "2.10.4",
    organization      :=  "com.example",
    scalacOptions     ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint"),
    incOptions        :=  incOptions.value.withNameHashing(true),
    doc in Compile    <<= target.map(_ / "none"),
    vagrantFile       :=  (baseDirectory in ThisBuild).value / ".." / "Vagrantfile"
  )

  private lazy val shared = general ++ Dependencies.resolvers ++ Testing.settings

  private val finatraSettings = Seq(
    mainClass in (Compile, run) := Some("com.example.finatra.Main")
  )

  lazy val root     = shared ++ Publish.noop
  lazy val common   = shared ++ Publish.settings ++ Dependencies.common
  lazy val testkit  = shared ++ Publish.noop     ++ Dependencies.testkit
  lazy val core     = shared ++ Publish.settings ++ Dependencies.core
  lazy val finatra  = shared ++ Publish.settings ++ Dependencies.finatra ++ Vagrant.settings ++ finatraSettings
}
