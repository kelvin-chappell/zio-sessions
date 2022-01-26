package session1

import zio._

import scala.annotation.tailrec

object Exercise4 extends ZIOAppDefault {

  def calcSumUpTo(n: Int): Long = if (n < 0) throw new RuntimeException("too low") else (1 to n).sum

  @tailrec
  def highestPrimeBelow(n: Int): Int = {
    def isPrime(num: Int): Boolean = (2 until num).forall(v => num % v != 0)
    if (n < 2) throw new RuntimeException("too low")
    else if (isPrime(n - 1)) n - 1
    else highestPrimeBelow(n - 1)
  }

  val program: ZIO[Console, Throwable, Unit] = for {
    (n, m) <- ZIO.attempt(highestPrimeBelow(1)).zipPar(ZIO.attempt(calcSumUpTo(-1)))
    _      <- Console.printLine(n)
    _      <- Console.printLine(m)
  } yield ()

  val run: ZIO[ZEnv with ZIOAppArgs, Any, Any] = program.foldZIO(
    failure => Console.printLine(failure.getMessage),
    _ => Console.printLine("Success!")
  )
}
