import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.SbtNativePackager.NativePackagerKeys._

object Packager {

  import BuildKeys._

  private lazy val dist = com.typesafe.sbt.SbtNativePackager.NativePackagerKeys.dist
  private lazy val publishDist = taskKey[File]("publish-dist")

  lazy val settings = packagerSettings ++ packageArchetype.java_server ++ Seq(
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := false,
    publishArtifact in (Compile, packageBin) := false,
    publish <<= (publish) dependsOn dist,
    publishLocal <<= (publishLocal) dependsOn dist,

    mappings in Universal <++= (sourceDirectory, packagerConfDirResources) map { (src, confs) =>
      val resources = src / "main" / "resources"
      confs.map(name => resources / name -> s"conf/$name")
    },

    artifact in publishDist ~= {
      (art: Artifact) => art.copy(`type` = "zip", extension = "zip")
    },

    publishDist <<= (target in Universal, normalizedName, version) map { (targetDir, id, version) =>
      val packageName = "%s-%s" format(id, version)
      targetDir / (packageName + ".zip")
    }
  ) ++ addArtifact(artifact in publishDist, publishDist)
}
