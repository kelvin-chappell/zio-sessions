package session1done

import zio._

import java.time.LocalDate

object Exercise1 extends scala.App {

  val program: ZIO[ZEnv, Throwable, Unit] =
    for {
      _         <- Console.print("Date: ")
      dateStr   <- Console.readLine
      date      <- ZIO.attempt(LocalDate.parse(dateStr))
      daysToAdd <- Random.nextLongBounded(90).tap(n => Console.printLine(s"Adding $n days"))
      _         <- Console.printLine(date.plusDays(daysToAdd))
    } yield ()

  val runtime = Runtime.default

  runtime.unsafeRun(
    program.foldZIO(
      failure => Console.printLine(s"Failed: $failure"),
      success => Console.printLine(s"Success: $success")
    )
  )
}
