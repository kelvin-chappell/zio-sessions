package session2done

import zio._

object Main extends ZIOAppDefault {

  private val program = for {
    _     <- Console.print("\nQuery type: ")
    qType <- Console.readLine
    _     <- Console.print("\nQuery: ")
    q     <- Console.readLine
    results <- qType match {
      case "c" => Capi.content(q).map(_.map(_.webTitle))
      case "t" => Capi.tags(q).map(_.map(_.id))
    }
    _ <- ZIO.foreachDiscard(results)(Console.printLine(_))
  } yield ()

  override def run: ZIO[ZEnv, Throwable, Unit] = program.provideCustom(CapiLive.layer).forever
}
