ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "zio-sessions",
    libraryDependencies ++= Seq(
      "dev.zio"  %% "zio"                        % "2.0.0-RC3",
      "com.gu"   %% "content-api-client-default" % "17.25.0",
      "org.slf4j" % "slf4j-nop"                  % "1.7.36" % Runtime
    )
  )
