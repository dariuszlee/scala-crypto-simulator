import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
// import scala.io.StdIn
import scala.concurrent.Future
import akka.actor.{ ActorSystem, ActorRef }
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

object WebServer {
  def main(args: Array[String]) {
    import ClientRequestHandler._
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val handler : ActorRef = system.actorOf(ClientRequestHandler.props, "requestHandler")

    val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
        Http().bind(interface = "localhost", port = 8080)
    val bindingFuture : Future[Http.ServerBinding] =
      serverSource.to(Sink.foreach { connection => // foreach materializes the source
        handler ! Request("asdf")
        println("Accepted new connection from " + connection.remoteAddress)
      }).run()

    // val route =
    //   path("") {
    //     get {
    //       complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    //     }
    //   }

    // val bindingFuture = Http().bindAndHandle(route, "localhost", 3100)

    // println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    // StdIn.readLine() // let it run until user presses return
    // bindingFuture
    //   .flatMap(_.unbind()) // trigger unbinding from the port
    //   .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
