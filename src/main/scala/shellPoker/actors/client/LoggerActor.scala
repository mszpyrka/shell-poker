package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, Props}

object LoggerActor {

  def props(formatter: ActorRef): Props = Props(new LoggerActor(formatter))
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class LoggerActor(val formatter: ActorRef) extends Actor {

  override def receive: Receive = {

  }

}
