import sbt._
import sbt.Keys._

object Dependencies {

  private val specs2        = "org.specs2"              %% "specs2"           % "2.4.1"
  private val mockito       = "org.mockito"             %  "mockito-all"      % "1.9.5"
  // private val dispatch      = "net.databinder.dispatch" %% "dispatch-core"    % "0.11.0"
  private val json4s        = "org.json4s"              %% "json4s-native"    % "3.2.9"
  private val logback       = "ch.qos.logback"          %  "logback-classic"  % "1.1.1"
  private val elasticsearch = "org.elasticsearch"       %  "elasticsearch"    % "1.2.1"
  private val jodatime      = "joda-time"               %  "joda-time"        % "2.3"
  private val jodaconvert   = "org.joda"                %  "joda-convert"     % "1.6"
  private val config        = "com.typesafe"            %  "config"           % "1.2.0"
  // private val commonsLang   = "org.apache.commons"      %  "commons-lang3"    % "3.3.1"
  // private val commonsIo     = "commons-io"              %  "commons-io"       % "2.4"
  private val finatraDep    = "com.twitter"             %% "finatra"          % "1.5.3"
  private val finagleCore   = "com.twitter"             %% "finagle-core"     % "6.13.1"
  private val finagleHttp   = "com.twitter"             %% "finagle-http"     % "6.13.1"
  private val twitterUtil   = "com.twitter"             %% "util-core"        % "6.12.1"

  private val specs2Test  = specs2  % "test,it,e2e"
  private val mockitoTest = mockito % "test,it,e2e"

  val resolvers = Seq(Keys.resolvers += "twttr" at "http://maven.twttr.com/")

  val root            = deps()
  val core            = deps(elasticsearch, jodatime, jodaconvert, config, specs2Test, mockitoTest, finagleCore, finagleHttp, twitterUtil, logback)
  val coreTestkit     = deps(specs2, mockito, elasticsearch, json4s)
  val finatra         = deps(finatraDep, specs2Test, config, json4s, logback)
  val finatraTestkit  = deps(finatraDep, specs2, mockito, json4s)

  private def deps(modules: ModuleID*): Seq[Setting[_]] = Seq(libraryDependencies ++= modules)
}
