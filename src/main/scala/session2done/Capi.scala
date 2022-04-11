package session2done

import com.gu.contentapi.client.model.v1.{Content, Tag}
import zio._

trait Capi {
  def content(q: String): ZIO[Any, Throwable, List[Content]]
  def tags(q: String): ZIO[Any, Throwable, List[Tag]]
}

object Capi {
  def content(q: String): ZIO[Capi, Throwable, List[Content]] = ZIO.serviceWithZIO(_.content(q))
  def tags(q: String): ZIO[Capi, Throwable, List[Tag]]        = ZIO.serviceWithZIO(_.tags(q))
}
