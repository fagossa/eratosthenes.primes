import sbt._

object Dependencies {

  object Akka {
    lazy val version     = "2.5.8"
    lazy val Typed       = "com.typesafe.akka" %% "akka-typed"   % version
    lazy val TestKit     = "com.typesafe.akka" %% "akka-testkit" % version
  }

  lazy val ScalaTest = "org.scalatest"     %% "scalatest"    % "3.0.1" % "test"

}
