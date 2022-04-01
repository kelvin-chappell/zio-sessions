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

object CapiLive {

  val layer: ZLayer[System, Throwable, Capi] = ZLayer.fromZIO {
    for {
      optApiKey <- zio.System.env("API_KEY")
      apiKey    <- ZIO.fromOption(optApiKey).orElseFail(new IllegalArgumentException("No API key in environment"))
      client = new GuardianContentClient(apiKey)
    } yield new Capi {
      override def search(q: String): ZIO[Any, Throwable, List[Content]] = ZIO.fromFuture { _ =>
        val search = ContentApiClient.search.q(q)
        client.getResponse(search).map(_.results.toList)
      }
    }
  }
}
