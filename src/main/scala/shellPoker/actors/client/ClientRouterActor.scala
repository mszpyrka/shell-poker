package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import shellPoker.actors.ActorAddress
import shellPoker.actors.client.ClientRouterActor.{ActionRequest, ActionResponse, LoginRequest, RegisterPlayer}
import shellPoker.actors.client.LoggerActor.LoggerMessage
import shellPoker.gameEngine.playerAction._

object ClientRouterActor {

  def props(serverAddress: ActorAddress): Props = Props(new ClientRouterActor(serverAddress))

  final case object ActionRequest
  final case class ActionResponse(action: Action)
  final case object LoginRequest
  final case class RegisterPlayer(name: String)
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class ClientRouterActor(val serverAddress: ActorAddress) extends Actor {

  // Actor reference for child LoggerActor instance
  val logger: ActorRef = context.actorOf(LoggerActor.props(this.self))
  val inputReader: ActorRef = context.actorOf(InputReaderActor.props)


  // Stores reference to the actor that requested action from user
  private var lastAsker: ActorRef = _


  override def receive: Receive = {

    // Messages received from system supervisor:
    case register: RegisterPlayer => serverAddress.selection(context) ! ???

    // Messages received from user and sent to server system:
    case actionResponse: ActionResponse => lastAsker ! actionResponse

    // Messages received from server and sent to logger:
    case log: LoggerMessage => logger ! log
    case ActionRequest => {

      lastAsker = sender()
      logger ! ActionRequest
    }
  }
}