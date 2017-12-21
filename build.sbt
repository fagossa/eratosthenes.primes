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
    name := "erasthosthenes-akka",
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
  )
  .settings(
    fork in run := true,
    fmtSettings,
    scalacOptions ++= scalaCSettings,
    libraryDependencies ++= Seq(Akka, AkkaTestKit, ScalaTest)
  )

lazy val fmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtOnCompile.in(Sbt) := false,
    scalafmtVersion := "1.3.0"
  )

lazy val scalaCSettings =
  Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-Xfatal-warnings"
  )