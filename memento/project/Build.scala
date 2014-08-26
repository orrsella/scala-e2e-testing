import sbt._
import sbt.Keys._

object TheBuild extends Build {

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("root", file("."))
    .aggregate(core, coreTestkit, finatra, finatraTestkit)
    .configs(Configs.all: _*)
    .settings(Settings.root: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val core = Project("memento-core", file("memento-core"))
    .dependsOn(coreTestkit % "test,it,e2e->compile")
    .configs(Configs.all: _*)
    .settings(Settings.core: _*)
    // .settings(unmanagedClasspath in Test <++= (fullClasspath in (LocalProject("memento-core-testkit"), Compile))) // to avoid cyclic refence

  lazy val coreTestkit = Project("memento-core-testkit", file("memento-core-testkit"))
    // .dependsOn(core)
    .configs(Configs.all: _*)
    .settings(Settings.coreTestkit: _*)

  lazy val finatra = Project("memento-finatra", file("memento-finatra"))
    .dependsOn(core % "*", finatraTestkit % "test,it,e2e->compile")
    .configs(Configs.all: _*)
    .settings(Settings.finatra: _*)

  lazy val finatraTestkit = Project("memento-finatra-testkit", file("memento-finatra-testkit"))
    .dependsOn(core)
    .configs(Configs.all: _*)
    .settings(Settings.finatraTestkit: _*)
}
