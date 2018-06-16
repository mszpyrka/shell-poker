package shellPoker.gameEngine.gameplay

import shellPoker.gameEngine.InvalidInputException
import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.playerAction.{Action, ActionValidation, Legal}
import shellPoker.gameEngine.table.{Pot, TableSeat}
import shellPoker.userCommunication.Parser

class LocalTestSupervisor(gameState: GameState) extends HandSupervisor(gameState) {

  private def seatFormat(seat: TableSeat): String = {

    var result: String = seat.seatNumber + ": "

    if (seat == gameState.table.dealerButton)
      result += "D \t"

    else if (seat == gameState.table.bigBlind)
      result += "BB\t"

    else if (seat == gameState.table.smallBlind)
      result += "SB\t"

    else
      result += "  \t"

    if (!seat.isEmpty)
      result += "(" + seat.player.chipStack.chipCount + ")\t" + seat.player.name + "\tbet: " + seat.player.currentBetSize

    else
      result += "(empty)"

    if (!seat.isEmpty && seat.player.hasFolded) {

      var tmpResult = ""

      for (c: Char <- result)
        tmpResult += "\u0336" + c.toString

      return tmpResult
    }

    result
  }

  private def potFormat(pots: List[Pot]): String = {

    var result: String = "Pot: "
    for (i <- pots.indices)
      result += "(" + i + " ~ " + pots(i).size + ") "

    result
  }


  override def logHandStatus(gameState: GameState): Unit = {

    println("status:")
    println("table: " + gameState.table.communityCards)
    println(potFormat(gameState.table.potManager.pots))
    gameState.table.seats.foreach((seat: TableSeat) => println(seatFormat(seat)))
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

}
