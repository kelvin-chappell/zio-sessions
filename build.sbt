import Dependencies._

ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "zio-sessions",
    libraryDependencies += zio
  )
