import sbt.Keys.scalaVersion

import Dependencies._

lazy val root = project.in(file("."))
  .settings(
    inThisBuild(
      Seq(
        organization := "com.example",
        version := "1.0",
        scalaVersion := "2.12.2",
        version := "0.1"
      )
    ),
    name := "erasthosthenes-akka"
  )
  .settings(
    libraryDependencies ++= Seq(Akka, AkkaTestKit, ScalaTest)
  )
