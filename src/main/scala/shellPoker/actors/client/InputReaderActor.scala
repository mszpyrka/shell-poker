package shellPoker.actors.client

import akka.actor.{Actor, Props}
import shellPoker.gameEngine.playerAction._


object InputReaderActor {

  def props: Props = Props(new InputReaderActor)
}


/* Actor that constantly blocks on user's input and sends every user command to router actor.
 * This actor is created in client's actor system as a direct child of ClientRouterActor.
 */
class InputReaderActor extends Actor {

  override def receive: Receive = {

  }


  /* Actual main actor's method. */
  private def run(): Unit = {

    // Infinite loop reading user's commands
    while(true) {

      try {

        // Reads user's command, creates proper action and sends message to parent.
        val command: String = scala.io.StdIn.readLine()
        val action: Action = parseAction(command)
        context.parent ! action

      } catch {

        case EmptyLineException => ()
        case e: InvalidInputException => println(e.getMessage)
      }
    }
  }


  /* Parses raw string to Action instance. */
  private def parseAction(userInput: String): Action = {

    val splittedInput = userInput.toLowerCase().split(" ").filterNot(_ == "").toList

    if (splittedInput.isEmpty)
      throw EmptyLineException

    splittedInput.head match {
      case "crash" => throw new Exception("elo mordo")

      case "fold" => Fold

      case "check" => Check

      case "call" => Call

      case "all-in" => AllIn
      case "allin" => AllIn
      case "all" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1) == "in") AllIn
        else throw InvalidInputException("Invalid input, try again...")
      }

      case "bet" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1).matches("^[0-9]*$")) Bet(splittedInput(1).toInt)
        else throw InvalidInputException("You can only bet an integer number of chips...")
      }

      case "raise" => {
        if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
        else if (splittedInput(1).matches("^[0-9]*$")) Raise(splittedInput(1).toInt)
        else throw InvalidInputException("You can only raise an integer number of chips...")
      }

      case _ => throw InvalidInputException("Invalid input, try again...")
    }
  }
}
