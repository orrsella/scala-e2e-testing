import sbt._
import sbt.Keys._

object TheBuild extends Build {

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("root", file("."))
    .aggregate(common, testkit, core, finatra)
    .configs(Configs.all: _*)
    .settings(Settings.root: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val common: Project= Project("memento-common", file("memento-common"))
    .configs(Configs.all: _*)
    .settings(Settings.common: _*)

  lazy val testkit: Project= Project("memento-testkit", file("memento-testkit"))
    .dependsOn(common)
    .configs(Configs.all: _*)
    .settings(Settings.testkit: _*)

  lazy val core: Project= Project("memento-core", file("memento-core"))
    .dependsOn(common, testkit % "test,it,e2e->compile")
    .configs(Configs.all: _*)
    .settings(Settings.core: _*)

  lazy val finatra = Project("memento-finatra", file("memento-finatra"))
    .dependsOn(common, core % "*", testkit % "test,it,e2e->compile")
    .configs(Configs.all: _*)
    .settings(Settings.finatra: _*)
}
