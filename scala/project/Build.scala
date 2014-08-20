import sbt._
import sbt.Keys._

object TheBuild extends Build {

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("root", file("."))
    .aggregate(test, core, finatra)
    .configs(Configs.all: _*)
    .settings(Settings.root: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val test = Project("memento-test", file("memento-test"))
    .configs(Configs.all: _*)
    .settings(Settings.test: _*)

  lazy val core = Project("memento-core", file("memento-core"))
    .dependsOn(test % "test,it,e2e")
    .configs(Configs.all: _*)
    .settings(Settings.core: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Server Module
  // -------------------------------------------------------------------------------------------------------------------

  lazy val finatra = Project("memento-finatra", file("memento-finatra"))
    .dependsOn(core, test % "test,it,e2e")
    .configs(Configs.all: _*)
    .settings(Settings.finatra: _*)
}
