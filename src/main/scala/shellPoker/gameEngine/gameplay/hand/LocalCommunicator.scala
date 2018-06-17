package shellPoker.gameEngine.gameplay.hand

import shellPoker.core.cards.Card
import shellPoker.gameEngine.InvalidInputException
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.CompleteHandResults
import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.playerAction.{Action, ActionValidation}
import shellPoker.gameEngine.table.{Pot, TableSeat}
import shellPoker.userCommunication.Parser

class LocalCommunicator extends HandSupervisorCommunicator {

  private def seatFormat(seat: TableSeat, gameState: GameState): String = {

    var result: String = seat.seatNumber + ": "

    if (seat == gameState.table.dealerButton)
      result += "\u24b9 "

    /*else if (seat == gameState.table.bigBlind)
      result += "\u24b7 "

    else if (seat == gameState.table.smallBlind)
      result += "\u24c8 "
*/
    else
      result += "  "

    if (!seat.isEmpty)
      result += "(bet: " + seat.player.currentBetSize + ")\t" + seat.player.name + " " +
        Parser.chipStackToUnicode(seat.player.chipStack, 10000)

    //else
    //  result += "(empty)"

    if (!seat.isEmpty && seat.player.hasFolded) {

      var tmpResult = ""

      for (c: Char <- result)
        tmpResult += "\u0336" + c.toString

      return tmpResult
    }

    result
  }

  private def potFormat(pots: List[Pot], gameState: GameState): String = {

    var result: String = "Pot: "
    for (i <- pots.indices)
      result += "(" + i + " ~ " + pots(i).size + ") "

    result
  }


  override def logHandStatus(gameState: GameState): Unit = {

    println("========================================================================")
    println("status:")
    print("table: ")
    gameState.table.communityCards.foreach((card: Card) => print(card.toString + " "))
    println("")
    println(potFormat(gameState.table.potManager.pots, gameState))
    gameState.table.seats.foreach((seat: TableSeat) => println(seatFormat(seat, gameState)))
    println("========================================================================")
    println("")
  }

  /** Prompts the player actor to return Action object */
  override def requestAction(player: Player): Action = {

    print(player.name + "'s turn...")
    println(player.holeCards)
    var invalidInput = true
    var action: Action = null

    while(invalidInput) {

      invalidInput = false

      try {

        val command: String = scala.io.StdIn.readLine()
        action = Parser.stringToAction(command)
      } catch {
        case e: InvalidInputException => invalidInput = true
      }
    }

    action
  }

  override def logAction(player: Player, action: Action): Unit = {

    print(player.name + ": ")
    println(action)
  }

  def logActionValidation(player: Player, validation: ActionValidation): Unit = {

    print(player.name + ": ")
    println(validation)
  }

  override def logGameStatus(gameState: GameState): Unit = println("hand: " + gameState.handNumber)

  override def logHandResults(
    results: CompleteHandResults,
    gameState: GameState): Unit = {



    println("")
    println("somebody got some chips XD")
    println("")
  }

}
