package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.GameState


/** Represents the type of ending when one player makes a bet and every other player folds.
  * The player that made the last bet mucks his cards, as no other player is in game.
  * He is awarded with all the chips from the pot.
  *
  * @param gameState The state of the game when there is no further action possibility.
  */
class AllFoldedToBetEnding(val gameState: GameState) extends HandEndingHelper(gameState, AllFoldedToBet) {

  /* Winning player mucks his cards. */
  override def proceedWithFinalActions(): Unit = {

    val table = gameState.table
    val winner = table.playersInHand.head
    winner.muckCards()
  }

  /*
  /* Gives whole pot to the only player in hand left. */
  override def calculateHandResults(): CompleteHandResults = {

    val table = gameState.table
    val pots: List[Pot] = table.potManager.pots
    val winner: Player = table.playersInHand.head
    val results = new CompleteHandResults(table.players)
    for (pot <- pots)
      results.addChipsToPlayer(winner, pot.size)

    results
  }
  */
}
