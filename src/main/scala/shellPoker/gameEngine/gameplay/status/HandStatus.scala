package shellPoker.gameEngine.gameplay.status

import shellPoker.core.cards.Card
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.player.PlayerId
import shellPoker.gameEngine.table.{Pot, TableSeat}
import shellPoker.userCommunication.Parser


object HandStatus {
  def apply(playerId: PlayerId, gameState: GameState) = {
    var result = ""
    result += "====================================================================\n"
    result += "communityCards: "
    gameState.table.communityCards.foreach((card: Card) => result += card.toString + " ")
    result += "\n"
    result += potFormat(gameState.table.potManager.pots, gameState)
    result += "\n"
    gameState.table.seats.foreach((seat: TableSeat) => result += seatFormat(seat, gameState) + "\n")
    result += "====================================================================\n"

    result += "your cards: " + gameState.table.players.find(_.id == playerId).orNull.holeCards + "\n"
    new HandStatus(result)
  }

  private def potFormat(pots: List[Pot], gameState: GameState): String = {

    var result: String = "Pot: "
    for (i <- pots.indices)
      result += "(" + i + " ~ " + pots(i).size + ") "

    result
  }

  private def seatFormat(seat: TableSeat, gameState: GameState): String = {

    var result: String = seat.seatNumber + ": "

    if (seat == gameState.table.dealerButton)
      result += "\u24b9 "
    else
      result += "  "

    if (!seat.isEmpty)
      if(seat.player.id == gameState.actionTaker.id)
        result += "(bet: " + seat.player.currentBetSize + ")\t" + s"${Console.RED}" + seat.player.name + + s"${Console.RESET}" + "\t" +
          Parser.chipStackToUnicode(seat.player.chipStack, 10000)
      else
        result += "(bet: " + seat.player.currentBetSize + ")\t" + seat.player.name + "\t" +
          Parser.chipStackToUnicode(seat.player.chipStack, 10000)

    if (!seat.isEmpty && seat.player.hasFolded) {

      var tmpResult = ""

      for (c: Char <- result)
        tmpResult += "\u0336" + c.toString

      return tmpResult
    }

    result
  }
}

case class HandStatus private(result: String)
