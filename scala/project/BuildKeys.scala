import sbt._

object BuildKeys {

  lazy val testAll = TaskKey[Unit]("test-all")
  lazy val vagrantFile = SettingKey[File]("vagrant-file")
  lazy val packagerConfDirResources = SettingKey[Seq[String]]("conf-resources")
  // lazy val puppetRepoDir = settingKey[File]("puppet-repo-dir")
  // lazy val puppetSetModuleVersion = taskKey[Unit]("puppet-set-module-version")
}
