import play.sbt.routes.RoutesKeys

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.0"

ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-Xlint"
)

lazy val macros = (project in file("modules/macros")).settings(
  name := "play-wire-routes",
  libraryDependencies ++= Seq(
    "com.softwaremill.macwire" %% "macros" % "2.3.2" % "provided",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "com.typesafe.play" %% "play" % play.core.PlayVersion.current
  ),
  scalacOptions += "-Xfatal-warnings"
)

// This test came from https://github.com/playframework/play-samples/tree/2.8.x/play-scala-macwire-di-example
lazy val tests = (project in file("modules/tests"))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.macwire" %% "macros" % "2.3.2" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
    )
  ).dependsOn(macros)
