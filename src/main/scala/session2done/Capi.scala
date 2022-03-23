package session2done

import com.gu.contentapi.client.model.v1.Content
import com.gu.contentapi.client.{ContentApiClient, GuardianContentClient}
import zio._

import scala.concurrent.ExecutionContext.Implicits.global

trait Capi {
  def search(q: String): ZIO[Any, Throwable, List[Content]]
}

object Capi {
  def search(q: String): ZIO[Capi, Throwable, List[Content]] = ZIO.serviceWithZIO(_.search(q))
}

case class CapiLive(apiKey: String) extends Capi {
  override def search(q: String): ZIO[Any, Throwable, List[Content]] = ZIO.fromFuture[List[Content]] { _ =>
    val client      = new GuardianContentClient(apiKey)
    val toastSearch = ContentApiClient.search.q("cheese on toast")
    val x           = client.getResponse(toastSearch).map(_.results.toList)
    x
  }
}

object CapiLive {
  def layer(apiKey: String): ULayer[Capi] =
    (() => CapiLive(apiKey)).toLayer[Capi]
}
