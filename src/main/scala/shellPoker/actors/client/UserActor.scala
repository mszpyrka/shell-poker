package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import shellPoker.actors.client.InputReaderActor.Run
import shellPoker.actors.client.UserActor.Start
import shellPoker.actors._


object UserActor {

  def props(serverAddress: ActorAddress): Props = Props(new UserActor(serverAddress))
  final case object Start
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class UserActor(val serverAddress: ActorAddress) extends Actor {

  // Actor reference for child LoggerActor instance
  val logger: ActorRef = context.actorOf(LoggerActor.props(this.self))
  val inputReader: ActorRef = context.actorOf(InputReaderActor.props)

  def serverActor: ActorSelection = {

    val path = serverAddress.completeRemotePath
    context.actorSelection(path)
  }


  // Stores reference to the actor that requested action from user
  private var lastAsker: ActorRef = _


  override def receive: Receive = {

    case RoomStatusRequest => serverActor ! RoomStatusRequest

    case reg: Register => serverActor ! reg

    case Start => inputReader ! Run

    case message: ActionResponse => lastAsker ! message

    case ActionRequest =>
      lastAsker = sender()
      logger ! ActionRequest

    case other => logger ! other

  }
}