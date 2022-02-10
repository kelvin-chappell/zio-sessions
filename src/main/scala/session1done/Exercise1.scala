package session1done

import zio._

import java.time.LocalDate

/*
 * Write a ZIO program that reads a date string from the console,
 * adds a random number of days to it and prints the result to the console.
 */
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

  runtime.unsafeRun(program)
}
