import sbt._
import sbt.IO._
import sbt.Keys._
import scala.collection.immutable.Seq

object Settings {

  import BuildKeys._
  import Configs._

  private lazy val general = Seq(
    version           <<= version in ThisBuild,
    scalaVersion      :=  "2.10.4",
    organization      :=  "com.example",
    scalacOptions     ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint"),
    incOptions        :=  incOptions.value.withNameHashing(true),
    doc in Compile    <<= target.map(_ / "none"),
    vagrantFile       :=  (baseDirectory in ThisBuild).value / ".." / "Vagrantfile",
    ansibleRoot       :=  (baseDirectory in ThisBuild).value / ".." / "ansible",
    ansibleSetVersion :=  ()
  )

  private lazy val shared = general ++ Dependencies.resolvers ++ Testing.settings ++ Vagrant.settings

  private val finatraSettings = Seq(
    mainClass in (Compile, run)         := Some("com.example.finatra.Main"),
    ansibleVersionVariable              := "memento_finatra_version",
    ansibleVersionFile                  := ansibleRoot.value / "inventories" / "production" / "group_vars" / "appservers",
    ansibleVersionFile in EndToEndTest  := ansibleRoot.value / "inventories" / "vagrant"    / "group_vars" / "appservers",
    ansibleSetVersion                   := Yaml.setVariable(ansibleVersionFile.value, ansibleVersionVariable.value, version.value),
    ansibleSetVersion in EndToEndTest   := Yaml.setVariable((ansibleVersionFile in EndToEndTest).value, ansibleVersionVariable.value, version.value)
  )

  lazy val root     = shared ++ Publish.noop
  lazy val testkit  = shared ++ Publish.noop     ++ Dependencies.testkit
  lazy val core     = shared ++ Publish.settings ++ Dependencies.core
  lazy val finatra  = shared ++ Publish.settings ++ Dependencies.finatra ++ finatraSettings
}
