package session2done

import zio._

object Main extends ZIOAppDefault {

  val program = for {
    result <- Capi.search("term")
    _      <- Console.printLine(result)
  } yield ()

  override def run = program.provideCustom(CapiLive.layer("key"))
}
