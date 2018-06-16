package shellPoker.userCommunication

import shellPoker.gameEngine._

object Parser {
    def stringToAction(userInput: String): Action = {
    val splittedInput = userInput.toLowerCase().split(" ").filterNot(_ == "").toList
    
    splittedInput.head match {
      case "fold" => Fold

      case "check" => Check

      case "call" => Call

      case "all-in" => AllIn(0) //for now it's 0
      case "allin" => AllIn(0) //for now it's 0
      case "all" => {
          if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
          else if(splittedInput(1) == "in") AllIn(0) //for now it's 0
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