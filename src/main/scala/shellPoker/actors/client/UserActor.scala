package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, Props}
import shellPoker.actors.ActorAddress


object UserActor {

  def props(serverAddress: ActorAddress): Props = Props(new UserActor(serverAddress))
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class UserActor(val serverAddress: ActorAddress) extends Actor {

  // Actor reference for child LoggerActor instance
  val logger: ActorRef = context.actorOf(LoggerActor.props(this.self))
  val inputReader: ActorRef = context.actorOf(InputReaderActor.props)


  // Stores reference to the actor that requested action from user
  private var lastAsker: ActorRef = _


  override def receive: Receive = {

  }
}