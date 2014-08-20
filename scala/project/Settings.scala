import sbt._
import sbt.Keys._
import scala.collection.immutable.Seq

object Settings {

  import BuildKeys._

  private lazy val general = Seq(
    version         <<= version in ThisBuild,
    scalaVersion    :=  "2.10.4",
    organization    :=  "com.example",
    scalacOptions   ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint"),
    incOptions      :=  incOptions.value.withNameHashing(true),
    doc in Compile  <<= target.map(_ / "none")
  )

  private lazy val shared = general ++ Dependencies.resolvers ++ Testing.settings

  private val finatraSettings = Seq(
    mainClass in (Compile, run) := Some("com.example.finatra.Main"),
    vagrantFile := (baseDirectory in ThisBuild).value / "Vagrantfile",
    packagerConfDirResources := Seq("application.conf", "logback.xml")//,
    // puppetRepoDir := (baseDirectory in ThisBuild).value / "../spiderpic-puppet"
  )

  lazy val root     = shared ++ Dependencies.root
  lazy val test     = shared ++ Dependencies.test
  lazy val core     = shared ++ Dependencies.core
  lazy val finatra  = shared ++ Dependencies.finatra ++ Packager.settings ++ Vagrant.settings ++ finatraSettings
}
