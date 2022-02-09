package session1done

import zio._

import java.io.IOException
import java.time.LocalDateTime

object Main extends scala.App {

  val timeEffect: ZIO[Clock, Nothing, LocalDateTime] = Clock.localDateTime

  def rndEffect(time: LocalDateTime): ZIO[Random with Console, IOException, Int] =
    for {
      _ <- Console.printLine(s"Year: ${time.getYear}")
      i <- Random.nextIntBounded(time.getYear)
    } yield i

  val envEffect: ZIO[System, SecurityException, Option[String]] = System.env("envval")

  val simpleProgram: ZIO[Console, IOException, Unit] = Console.printLine("Hello world!")

  val complexProgram: ZIO[ZEnv, Exception, Unit] =
    for {
      e1    <- timeEffect.debug("time1")
      e2    <- rndEffect(e1)
      e3    <- timeEffect.repeatN(10).debug("time2")
      e4    <- envEffect.debug("env")
      _     <- Console.printLine(e2)
      _     <- Console.print("Date: ")
      input <- Console.readLine
      _     <- Console.printLine(input)
    } yield ()

  val runtime = Runtime.default

  runtime.unsafeRun(complexProgram)
}
