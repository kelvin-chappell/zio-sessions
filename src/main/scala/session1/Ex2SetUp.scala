package session1

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future, blocking}
import scala.concurrent.{Future, future}

// TODO
object Ex2SetUp extends scala.App {
  import java.util.concurrent.Executors
  import scala.concurrent._
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(50))

  def add(x: Int, y: Int) = Future(x + y)

  def multiply(x: Int, y: Int) = Future {
    val a      = addOne(x)
    val b      = addOne(y)
    val result = for (r1 <- a; r2 <- b) yield r1 * r2

    // This can dead-lock due to the limited size of our thread-pool!
    Await.result(result, Duration.fromNanos(1000))
  }

  import zio._

  ZIO.fromFuture(Future(1 + 2)(_))

  multiply(3, 4).map(n => println(n))

  object Cloud {
    def runAlgorithm(i: Int): Future[Int] = Future {
      Thread.sleep(scala.util.Random.nextLong(500))
      val result = i + 10
      println(s"returning result from cloud: $result")
      result
    }
  }
}
