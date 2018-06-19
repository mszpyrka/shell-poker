package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, Props}
import shellPoker.actors._

object LoggerActor {

  def props(formatter: ActorRef): Props = Props(new LoggerActor(formatter))
}


/** Responsible for receiving updates about current game state
  * and printing them properly formatted on user's console.
  */
class LoggerActor(val formatter: ActorRef) extends Actor {

  override def receive: Receive = {

    case ActionRequest => printActionRequest()
    case message: ValidationMessage => printValidation(message)
    case message: ActionLog => printActionLog(message)
    case message: HandStatusMessage => printHandStatus(message)
    case message: GameStatusMessage => printGameStatus(message)
    case message: ShowdownStatusMessage => printShowdownStatus(message)
    case message: EndingStatusMessage => printEndingStatus(message)
    case other => println("unknown message:");println(other)
  }


  // ===================================================================================================================
  // Actual formatting methods for all incoming messages:
  // ===================================================================================================================

  private def printActionRequest(): Unit =
    print("\u001b[42m" + "your turn: " + "\u001b[0m")

  private def printValidation(validation: ValidationMessage): Unit = println("\u001b[41m" + validation.validation + "\u001b[0m")

  private def printActionLog(log: ActionLog): Unit = println("\u001b[43m" + log.playerId.name + ": " + log.action + "\u001b[0m")

  private def printHandStatus(status: HandStatusMessage): Unit = println(status.handStatus.result)

  private def printGameStatus(status: GameStatusMessage): Unit = println(status.gameStatus.result)

  private def printShowdownStatus(status: ShowdownStatusMessage): Unit = println(status.showdownStatus.result)

  private def printEndingStatus(status: EndingStatusMessage): Unit = println(status.endingStatus.result)

}
