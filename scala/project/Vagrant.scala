import sbt._
import sbt.Keys._
import scala.language.postfixOps

object Vagrant {

  import BuildKeys._
  import Configs._

  private lazy val vagrant = settingKey[Vagrant]("vagrant")

  lazy val settings = Seq(
    test in EndToEndTest <<= (test in EndToEndTest).dependsOn(publishLocal),
    vagrant in EndToEndTest := new Vagrant(vagrantFile.value),
    testOptions in EndToEndTest += Tests.Setup(()   => if ((definedTests in EndToEndTest).value.nonEmpty) (vagrant in EndToEndTest).value.setup()),
    testOptions in EndToEndTest += Tests.Cleanup(() => if ((definedTests in EndToEndTest).value.nonEmpty) (vagrant in EndToEndTest).value.cleanup())
  )
}

class Vagrant(vagrantFile: File) {

  private var previousState: VagrantState = Unknown
  private val vagrantDir = vagrantFile.getParentFile

  def setup(): Unit = {
    previousState = state()
    previousState match {
      case Running    => provision()
      case Saved      => up(); provision()
      case NotCreated => up()
      case Unknown    => up()
    }
  }

  def cleanup(): Unit = if (previousState != Running) suspend()

  // cli method wrappers
  private def state(): VagrantState = {
    val res = Process("vagrant" :: "status" :: Nil, vagrantFile.getParentFile)!!

    if (res.contains("running (")) Running
    else if (res.contains("saved (")) Saved
    else if (res.contains("not created (")) NotCreated
    else Unknown
  }

  private def up(): Unit = Process("vagrant" :: "up" :: Nil, vagrantDir)!
  private def provision(): Unit = Process("vagrant" :: "provision" :: Nil, vagrantDir)!
  private def suspend(): Unit = Process("vagrant" :: "suspend" :: Nil, vagrantDir)!
  private def destroy(): Unit = Process("vagrant" :: "destroy" :: "-f" :: Nil, vagrantDir)!

  sealed trait VagrantState
  case object Running extends VagrantState
  case object Saved extends VagrantState
  case object NotCreated extends VagrantState
  case object Unknown extends VagrantState
}
