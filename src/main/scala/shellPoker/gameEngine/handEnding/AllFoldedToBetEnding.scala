package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.gameplay.GameState


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
}
