package session1done

import zio._

import scala.annotation.tailrec

/*
 * Write a ZIO program that will in parallel calculate the sum of numbers
 * up to a given Int value and the highest prime number below a given Int value
 * and then sum the result.
 */
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
    (p, s) <- ZIO.attempt(highestPrimeBelow(1)).zipPar(ZIO.attempt(calcSumUpTo(-1)))
    _      <- Console.printLine(p)
    _      <- Console.printLine(s)
  } yield ()

  val run: ZIO[ZEnv with ZIOAppArgs, Any, Any] = program.foldZIO(
    failure => Console.printLine(failure.getMessage),
    _ => Console.printLine("Success!")
  )
}
