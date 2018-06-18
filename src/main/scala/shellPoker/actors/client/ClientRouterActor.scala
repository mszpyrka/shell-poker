package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import shellPoker.actors.actorUtils.client.{LoggerFormatter, UserAction}
import shellPoker.actors.client.ClientRouterActor.{ActionRequest, ActionResponse, LoginRequest}
import shellPoker.actors.client.LoggerActor.LoggerMessage
import shellPoker.gameEngine.playerAction._

object ClientRouterActor {

  def props(formatter: LoggerFormatter): Props = Props(new ClientRouterActor)

  final case object ActionRequest
  final case class ActionResponse(action: Action)
  final case object LoginRequest
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class ClientRouterActor extends Actor {

  // Actor reference for child LoggerActor instance
  val loggerRef: ActorRef = context.actorOf(LoggerActor.props(this.self))
  private val serverSelection: ActorSelection = _

  override def preStart(): Unit = {

    ??? //todo: obtain server's selection

    serverSelection ! LoginRequest
  }


  // Stores reference to the actor that requested action from user
  private var lastAsker: ActorRef = _


  override def receive: Receive = {

    // Messages received from user and sent to server system:
    case actionResponse: ActionResponse => lastAsker ! actionResponse


    // Messages received from server and sent to logger:
    case log: LoggerMessage => loggerRef ! log
    case ActionRequest => {

      lastAsker = sender()
      loggerRef ! ActionRequest
    }
  }
}