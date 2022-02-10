package session1

import zio._

/*
 * Build a logging effect that will log a given message to the console
 * at INFO level with a timestamp.
 * And another effect that will log a throwable at the ERROR level.
 */
object Exercise3 extends ZIOAppDefault {

  def run: ZIO[ZEnv with ZIOAppArgs, Any, Any] = ???
}
