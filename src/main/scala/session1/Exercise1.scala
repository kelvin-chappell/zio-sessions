package session1

import zio._

import java.time.LocalDate

object Exercise1 extends scala.App {

  val program: ZIO[ZEnv, Throwable, Unit] =
    for {
      _         <- Console.print("Date: ")
      dateStr   <- Console.readLine
      date      <- ZIO.attempt(LocalDate.parse(dateStr))
      daysToAdd <- Random.nextLongBounded(90).tap(n => Console.printLine(s"Adding $n days"))
      transformed = date.plusDays(daysToAdd)
      _ <- Console.printLine(transformed)
    } yield ()

  val runtime = Runtime.default

  runtime.unsafeRun(program)
}
