import sbt._
import sbt.Keys._
import org.sbtidea.SbtIdeaPlugin
import org.sbtidea.SbtIdeaPlugin.ideaExtraTestConfigurations

object Testing {

  import BuildKeys._
  import Configs._

  private lazy val testSettings = Seq(
    fork in Test := false,
    parallelExecution in Test := false,
    ideaExtraTestConfigurations := Seq()
  )

  private lazy val itSettings = inConfig(IntegrationTest)(Defaults.testSettings) ++ Seq(
    fork in IntegrationTest := false,
    parallelExecution in IntegrationTest := false,
    scalaSource in IntegrationTest := baseDirectory.value / "src/it/scala",
    ideaExtraTestConfigurations ++= Seq(IntegrationTest)
  )

  private lazy val e2eSettings = inConfig(EndToEndTest)(Defaults.testSettings) ++ Seq(
    fork in EndToEndTest := false,
    parallelExecution in EndToEndTest := false,
    scalaSource in EndToEndTest := baseDirectory.value / "src/e2e/scala",
    ideaExtraTestConfigurations ++= Seq(EndToEndTest)
  )

  lazy val settings = testSettings ++ itSettings ++ e2eSettings ++ Seq(
    testAll <<= (test in EndToEndTest).dependsOn((test in IntegrationTest).dependsOn(test in Test))
  )
}
