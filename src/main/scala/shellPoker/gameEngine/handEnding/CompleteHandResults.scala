package shellPoker.gameEngine.handEnding

import shellPoker.core.cards.Card
import shellPoker.gameEngine.player.Player

/** Represents the results of all players at the table after completed hand.
  *
  */
class CompleteHandResults(val players: List[Player], communityCards: List[Card]) {

  val results: List[SinglePlayerHandResults] =
    for (player <- players) yield new SinglePlayerHandResults(player, communityCards)

  def addChipsToPlayerResults(player: Player, amount: Int): Unit = {

    val targetSlot: SinglePlayerHandResults = results.find(_.player ==  player).orNull
    targetSlot.addChips(amount)
  }
}
