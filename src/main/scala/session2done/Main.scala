package session2done

import zio._

object Main extends ZIOAppDefault {

  private val program = for {
    _       <- Console.print("\nQuery: ")
    q       <- Console.readLine
    results <- Capi.search(q)
    _       <- ZIO.foreachDiscard(results)(result => Console.printLine(result.webTitle))
  } yield ()

  override def run: ZIO[ZEnv, Throwable, Unit] = program.provideCustom(CapiLive.layer).forever
}
