import sbt._

object Dependencies {
  lazy val akkaVersion = "2.5.8"
  val AkkaTestKit    = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val Akka           = "com.typesafe.akka" %% "akka-typed"   % akkaVersion
  lazy val ScalaTest = "org.scalatest"     %% "scalatest"    % "3.0.1" % "test"
}
