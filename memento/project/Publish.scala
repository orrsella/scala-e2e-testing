import sbt._
import sbt.Keys._
import ohnosequences.sbt.SbtS3Resolver._
import ohnosequences.sbt.SbtS3Resolver.S3Resolver._
import scala.language.postfixOps
import com.amazonaws.services.s3.model.Region

object Publish {

  lazy val noop = Seq(
    publish       := (),
    publishLocal  := ()
  )

  lazy val settings = S3Resolver.defaults ++ Seq(
    crossPaths        :=  false,
    publishMavenStyle :=  false,

    s3region := Region.US_Standard,
    publishTo := {
      if (isSnapshot.value) None
      else Some(s3resolver.value("Repository", s3("TODO")) withIvyPatterns)
    }
  )
}
