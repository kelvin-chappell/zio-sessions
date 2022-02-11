package session1done

import zio._

import java.io.IOException
import java.time.LocalDateTime

object Main extends scala.App {

  val currentTime: ZIO[Clock, Nothing, LocalDateTime] = Clock.localDateTime

  val readEnvValue: ZIO[System, SecurityException, Option[String]] = System.env("envval")

  val genRndNumber: ZIO[Random, Nothing, Int] = {
    println("y")
    Random.nextIntBounded(9).debug("rnd value")
  }

  val program: ZIO[Console, IOException, Unit] = Console.printLine("Hello World")

  val printTime: ZIO[Clock with Console, IOException, Unit] =
    for {
      time <- currentTime
      _    <- Console.printLine(time.toString)
    } yield ()

  val printEnvVal: ZIO[System with Console, Exception, Unit] =
    for {
      envValue <- readEnvValue
      _        <- Console.printLine(envValue.toString)
    } yield ()

  val printRndNumber: ZIO[Random with Console, Exception, Unit] =
    for {
      rndNumber <- genRndNumber
      _         <- Console.printLine(rndNumber.toString)
    } yield ()

  val runtime = Runtime.default

  runtime.unsafeRun(printRndNumber.repeatN(10))
}
