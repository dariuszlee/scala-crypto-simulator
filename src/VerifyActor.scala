import akka.actor.{ Actor, ActorLogging, Props, ActorRef, ActorSystem }

object ClientRequestHandler { 
  def props : Props = Props[ClientRequestHandler]
  case class Request(data : String)
}

class ClientRequestHandler extends Actor with ActorLogging {
  import ClientRequestHandler._

  def receive = {
    case Request(data)  => log.info(data)
  }
}

object WorkerActor {
  def props : Props = Props[WorkerActor]
  case class WorkRequest(data : String)
}

class WorkerActor extends Actor {
  import WorkerActor._

  def receive = {
    case WorkerActor(data)  => {
    }
  }

}

object TestActor extends App {
  import ClientRequestHandler._

  val system: ActorSystem = ActorSystem("bcRequest")
  val handler : ActorRef = system.actorOf(ClientRequestHandler.props, "requestHandler")

  handler ! Request("asdf")
}
