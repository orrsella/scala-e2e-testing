import sbt._
import sbt.Keys._

object TheBuild extends Build {

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("root", file("."))
    .aggregate(testkit, core, finatra)
    .configs(Configs.all: _*)
    .settings(Settings.root: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val testkit = Project("memento-testkit", file("memento-testkit"))
    .dependsOn(core)
    .configs(Configs.all: _*)
    .settings(Settings.testkit: _*)

  lazy val core = Project("memento-core", file("memento-core"))
    .configs(Configs.all: _*)
    .settings(Settings.core: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Server Module
  // -------------------------------------------------------------------------------------------------------------------

  lazy val finatra = Project("memento-finatra", file("memento-finatra"))
    .dependsOn(core % "test,it,e2e", testkit % "test,it,e2e->compile")
    .configs(Configs.all: _*)
    .settings(Settings.finatra: _*)
}
