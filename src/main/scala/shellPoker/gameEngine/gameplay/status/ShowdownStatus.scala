package shellPoker.gameEngine.gameplay.status

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.PotDistributionHelper
import shellPoker.gameEngine.player.Player

object  ShowdownStatus {
  def apply(gameState: GameState) = {

    var result: String = ""

    val firstToShow: Player = gameState.roundEndingPlayer
    val orderedPlayers: List[Player] = PotDistributionHelper.getShowdownOrder(gameState.table, firstToShow)

    for (player <- orderedPlayers) {

      if (player.muckedCards)
        result += player.name + " mucked...\n"

      else if (player.showedCards)
        result += player.name + " showed " + player.holeCards + "\n"
    }

    new ShowdownStatus(result)
  }
}

case class ShowdownStatus private (result: String)
