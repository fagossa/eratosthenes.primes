import sbt._

object Dependencies {
  lazy val akkaVersion = "2.5.3"
  lazy val Akka = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val AkkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  lazy val ScalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
}
