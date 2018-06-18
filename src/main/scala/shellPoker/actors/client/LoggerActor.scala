package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, Props}
import shellPoker.actors.client.ClientRouterActor.ActionRequest
import shellPoker.actors.client.LoggerActor._
import shellPoker.core.cards.Card
import shellPoker.gameEngine.gameplay.status.{EndingStatus, GameStatus, HandStatus}

object LoggerActor {

  def props(formatter: ActorRef): Props = Props(new LoggerActor(formatter))

  sealed trait LoggerMessage
  final case object SuccessfulLoginMessage extends LoggerMessage
  final case object UnsuccessfulLoginMessage extends LoggerMessage
  final case class HoleCardsMessage(c1: Card, c2: Card) extends LoggerMessage
  final case class HandStatusMessage(handStatus: HandStatus) extends LoggerMessage
  final case class GameStatusMessage(gameStatus: GameStatus) extends LoggerMessage
  final case class EndingStatusMessage(endingStatus: EndingStatus) extends LoggerMessage
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class LoggerActor(val formatter: ActorRef) extends Actor {

  override def receive: Receive = {

    case ActionRequest => ???
    case SuccessfulLoginMessage => ???
    case UnsuccessfulLoginMessage => ???
    case HoleCardsMessage(c1: Card, c2: Card) => ???
    case HandStatusMessage(handStatus: HandStatus) => ???
    case GameStatusMessage(gameStatus: GameStatus) => ???
    case EndingStatusMessage(endingStatus: EndingStatus) => ???
    case other => ???
  }

}
