import sbt._
import sbt.Keys._

object Publish {

  // private val prefix = settingKey[String]("prefix")

  lazy val noop = Seq(
    publish       := (),
    publishLocal  := (),
    publishTo     := Some(Resolver.url("spiderpic")) // dummy resolver so that sbt-release won't fail
  )

  lazy val settings = Seq(
    crossPaths        :=  false,
    publishMavenStyle :=  true,
    // s3credentials     :=  file(System.getProperty("user.home")) / ".sbt" / ".s3credentials",
    publishLocal      <<= publishM2//,
    // prefix            :=  (if (isSnapshot.value) "snapshots" else "releases"),
    // publishTo         :=  Some(s3resolver.value(s"spiderpic ${prefix.value}", s3(s"repo.spiderpic.com/${prefix.value}")))
  )
}
