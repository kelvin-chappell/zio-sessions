package session2done

import com.gu.contentapi.client.model.v1.{Content, Tag}
import com.gu.contentapi.client.{ContentApiClient, GuardianContentClient}
import zio._

object CapiLive {

  val layer: ZLayer[zio.System, Throwable, Capi] = ZLayer.fromZIO {
    for {
      optApiKey <- zio.System.env("API_KEY")
      apiKey    <- ZIO.fromOption(optApiKey).orElseFail(new IllegalArgumentException("No API key in environment"))
      client = new GuardianContentClient(apiKey)
    } yield new Capi {

      override def content(q: String): ZIO[Any, Throwable, List[Content]] = ZIO.fromFuture { implicit ec =>
        val search = ContentApiClient.search.q(q)
        client.getResponse(search).map(_.results.toList)
      }

      override def tags(q: String): ZIO[Any, Throwable, List[Tag]] = ZIO.fromFuture { implicit ec =>
        val search = ContentApiClient.tags.q(q)
        client.getResponse(search).map(_.results.toList)
      }
    }
  }
}
