import sbt.url

ThisBuild / organization := "com.github.hiroshi-cl"
ThisBuild / organizationName := "hiroshi-cl"
ThisBuild / organizationHomepage := Some(url("https://github.com/hiroshi-cl"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/hiroshi-cl/play-routes-wire"),
    "scm:git@github.com:hiroshi-cl/play-routes-wire.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "hiroshi-cl",
    name = "Hiroshi Yamaguchi",
    email = "",
    url = url("https://github.com/hiroshi-cl")
  )
)

ThisBuild / description := "An ad-hoc and tiny macro project for Play Framework with MacWire"
ThisBuild / licenses := List("BSD 2-Clause" -> url("http://opensource.org/licenses/BSD-2-Clause"))
ThisBuild / homepage := Some(url("https://github.com/hiroshi-cl/play-routes-wire"))

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishMavenStyle := true
