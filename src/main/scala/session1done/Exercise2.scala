package session1done

import zio._

import java.time.LocalDate

object Exercise2 extends ZIOAppDefault {

  def toDate(dateStr: String): ZIO[Clock, Nothing, LocalDate] =
    ZIO
      .attempt(LocalDate.parse(dateStr))
      .catchAll(_ => Clock.localDateTime.map(_.toLocalDate))

  val run: ZIO[ZEnv with ZIOAppArgs, Any, Any] = for {
    _         <- Console.print("Date: ")
    dateStr   <- Console.readLine
    date      <- toDate(dateStr)
    daysToAdd <- Random.nextLongBounded(90).debug("daysToAdd")
    _         <- Console.printLine(date.plusDays(daysToAdd))
  } yield ()
}
