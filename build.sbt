import scala.util.Try

val playVersion = play.core.PlayVersion.current
val scala211 = "2.11.12"
val scala212 = "2.12.10"
val scala213 = "2.13.1"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := (playVersion.split('.').toSeq match {
  case "2" +: "6" +: _ => scala212
  case "2" +: "7" +: _ => scala212
  case "2" +: "8" +: _ => scala213
})

ThisBuild / crossScalaVersions := (playVersion.split('.').toSeq match {
  case "2" +: "6" +: _ => Seq(scala211, scala212)
  case "2" +: "7" +: x +: _ =>
    Seq(scala211, scala212) ++ Try(x.toInt).filter(_ >= 3).map(_ => Seq(scala213)).getOrElse(Seq.empty)
  case "2" +: "8" +: _ => Seq(scala212, scala213)
})

ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-Xlint"
)

lazy val macros = (project in file("modules/macros")).settings(
  name := "play-wire-routes",
  libraryDependencies ++= Seq(
    "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided",
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
      "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
    )
  ).dependsOn(macros)
