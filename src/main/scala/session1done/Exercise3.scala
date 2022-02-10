package session1done

import zio._

import java.io.IOException
import java.time.LocalDate

/*
 * Build a logging effect that will log a given message to the console
 * at INFO level with a timestamp.
 * And another effect that will log a throwable at the ERROR level.
 */
object Exercise3 extends ZIOAppDefault {

  def logInfo(message: String): ZIO[Console with Clock, IOException, Unit] =
    Clock.localDateTime.flatMap(timestamp => Console.printLine(s"$timestamp: INFO: $message"))

  def logError(t: Throwable): ZIO[Console with Clock, IOException, Unit] =
    Clock.localDateTime.flatMap(timestamp => Console.printLine(s"$timestamp: ERROR: ${t.getMessage}"))

  val run: ZIO[ZEnv with ZIOAppArgs, Any, Any] = (for {
    _       <- Console.print("Date: ")
    dateStr <- Console.readLine
    date    <- ZIO.attempt(LocalDate.parse(dateStr))
  } yield date).foldZIO(
    failure => logError(failure),
    success => logInfo(success.toString)
  )
}
