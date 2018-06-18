package shellPoker.userCommunication

import shellPoker.actors.client.InvalidInputException
import shellPoker.gameEngine._
import shellPoker.core.cards._
import shellPoker.gameEngine.playerAction._
import shellPoker.gameEngine.player._

import scala.util.Random

object Parser {
    def stringToAction(userInput: String): Action = {
    val splittedInput = userInput.toLowerCase().split(" ").filterNot(_ == "").toList

      if (splittedInput.isEmpty)
        throw InvalidInputException("Invalid input, try again...")
    
    splittedInput.head match {
      case "crash" => throw new Exception("elo mordo")

      case "fold" => Fold

      case "check" => Check

      case "call" => Call

      case "all-in" => AllIn
      case "allin" => AllIn
      case "all" => {
          if (splittedInput.size < 2) throw InvalidInputException("Invalid input, try again...")
          else if(splittedInput(1) == "in") AllIn
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

  def chipStackToUnicode(chipStack: ChipStack, startingChipCount: Int): String = {

    val chipUnicodes: List[String] = List("\u26c0", "\u26c1", "\u26c2", "\u26c3")

    val getRandomChipUnicode: (() => String) = () => Random.shuffle(chipUnicodes).head

    val generateChipStack: (Int => String) = (stacksNum: Int) => {
      var res: String = ""
      for(_ <- 0 until stacksNum) res += getRandomChipUnicode()
      res
    }

    var result: String = ""
    val chips: Int = chipStack.chipCount

    if(chips == 0) result += ""

    else if (chips > 0 && chips < 0.1 * startingChipCount) 
      result += generateChipStack(1)

    else if (chips >= 0.1 * startingChipCount && chips < 0.5 * startingChipCount) 
      result += generateChipStack(2)

    else if (chips >= 0.5 * startingChipCount && chips <  startingChipCount) 
      result += generateChipStack(6)

    else if (chips == startingChipCount)
      result += generateChipStack(8)

    else if (chips > startingChipCount && chips <= 1.5 * startingChipCount ) 
      result += generateChipStack(10)

    else if (chips > 1.5 * startingChipCount) 
      result += generateChipStack(12)


    
    result += s" ~ ($chips)"

    result
  }
}