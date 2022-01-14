package session1

import zio._

import java.io.IOException
import java.time.LocalDateTime

// TODO
object Exercise1 extends scala.App {

  val timeEffect: ZIO[Clock, Nothing, LocalDateTime] = Clock.localDateTime

  def parseEffect(s: String): ZIO[Any, Throwable, LocalDateTime] =
    ZIO.attempt(LocalDateTime.parse(s))

  def rndEffect(time: LocalDateTime): ZIO[Random with Console, IOException, Int] =
    for {
      i <- Random.nextInt
    } yield time.plusDays(i)

  val program: ZIO[ZEnv, Exception, Unit] =
    for {
      _       <- Console.print("Date: ")
      dateStr <- Console.readLine
      date    <- parseEffect(dateStr)
      _       <- Console.printLine(date)
    } yield ()

  val runtime = Runtime.default

  runtime.unsafeRun(program)
}
