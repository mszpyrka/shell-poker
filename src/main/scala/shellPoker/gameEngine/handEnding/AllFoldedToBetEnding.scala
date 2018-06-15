package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.{PokerTable, Pot}

/** Represents the type of ending when one player makes a bet and every other player folds.
  * No final actions are needed to take in this case and the player that made the bet is awarded
  * with all chips from the pot.
  *
  * @param table Table at which the game is played.
  */
class AllFoldedToBetEnding(val table: PokerTable) extends HandEndingHelper(table, AllFoldedToBet) {

  /* Does not take any actions. */
  override def proceedWithFinalActions(): Unit = ()

  /* Gives whole pot to the only player in hand left. */
  override def calculateHandResults(): CompleteHandResults = {

    val pots: List[Pot] = table.potManager.pots
    val winner = table.playersInHand.head
    val results = new CompleteHandResults(table.players)
    for (pot <- pots)
      results.addChipsToPlayer(winner, pot.size)

    results
  }
}
