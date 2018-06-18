package shellPoker.actors.client

import akka.actor.{Actor, ActorRef, Props}
import shellPoker.actors.{ActionResponse, Register, RoomStatusRequest}
import shellPoker.actors.client.InputReaderActor.Run
import shellPoker.gameEngine.player.PlayerId
import shellPoker.gameEngine.playerAction._


object InputReaderActor {

  def props: Props = Props(new InputReaderActor)

  final case object Run
}


/* Actor that constantly blocks on user's input and sends every user command to router actor.
 * This actor is created in client's actor system as a direct child of ClientRouterActor.
 */
class InputReaderActor extends Actor {

  def parent: ActorRef = context.parent

  override def receive: Receive = {

    case Run => run()
  }


  /* Actual main actor's method. */
  private def run(): Unit = {

    // Infinite loop reading user's commands
    while(true) {

      try {

        // Reads user's command, creates proper action and sends message to parent.
        val command: String = scala.io.StdIn.readLine()
        parseCommand(command)

      } catch {

        case EmptyLineException => ()
        case e: InvalidInputException => println(e.getMessage)
      }
    }
  }


  /* Parses raw string to Action instance. */
  private def parseCommand(userInput: String): Unit = {

    val splittedInput = userInput.toLowerCase().split(" ").filterNot(_ == "").toList

    if (splittedInput.isEmpty)
      throw EmptyLineException

    splittedInput.head match {


      case "crash" => throw new Exception("elo mordo")

      case "fold" => parent ! ActionResponse(Fold)

      case "check" => parent ! ActionResponse(Check)

      case "call" => parent ! ActionResponse(Call)

      case "all-in" => parent ! ActionResponse(AllIn)
      case "allin" => parent ! ActionResponse(AllIn)
      case "all" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1) == "in") parent ! ActionResponse(AllIn)
        else throw InvalidInputException("Invalid input, try again...")
      }

      case "bet" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1).matches("^[0-9]*$")) parent ! ActionResponse(Bet(splittedInput(1).toInt))
        else throw InvalidInputException("You can only bet an integer number of chips...")
      }

      case "raise" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1).matches("^[0-9]*$")) parent ! ActionResponse(Raise(splittedInput(1).toInt))
        else throw InvalidInputException("You can only raise an integer number of chips...")
      }

      case "register" => {
        if (splittedInput.size != 3) throw InvalidInputException("Invalid input, try again...")
        else {

          val name = splittedInput(1)
          val seatNo = splittedInput(2).toInt
          parent ! Register(PlayerId(name, seatNo))
        }
      }

      case _ => throw InvalidInputException("Invalid input, try again...")
    }
  }
}
