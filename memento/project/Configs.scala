import sbt._

object Configs {
  val IntegrationTest = config("it")
  val EndToEndTest = config("e2e")
  val all = Seq(IntegrationTest, EndToEndTest)
}
