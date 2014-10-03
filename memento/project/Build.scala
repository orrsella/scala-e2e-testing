import sbt._
import sbt.Keys._

object TheBuild extends Build {

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("memento-root", file("."))
    .aggregate(testkit, core, finatra)
    .configs(Configs.all: _*)
    .settings(Settings.root: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val testkit: Project = Project("memento-testkit", file("memento-testkit"))
    .settings(unmanagedSourceDirectories in Compile <++= (unmanagedSourceDirectories in (LocalProject("memento-core"), Compile))) // to avoid cyclic refence
    .configs(Configs.all: _*)
    .settings(Settings.testkit: _*)

  lazy val core: Project = Project("memento-core", file("memento-core"))
    .dependsOn(testkit % "test,it,e2e")
    .configs(Configs.all: _*)
    .settings(Settings.core: _*)

  lazy val finatra = Project("memento-finatra", file("memento-finatra"))
    .dependsOn(core, testkit % "test,it,e2e")
    .configs(Configs.all: _*)
    .settings(Settings.finatra: _*)
}
