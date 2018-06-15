package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.PokerTable

/** Represents the type of ending when one player makes a bet and every other player folds.
  * No final actions are needed to take in this case and the player that made the bet is awarded
  * with all chips from the pot.
  *
  * @param table Table at which the game is played.
  */
class AllFoldedToBetEnding(val table: PokerTable) extends HandEndingHelper(table, AllFoldedToBet) {

  override def proceedWithFinalActions(): Unit = ???

  override def distributePot(): Unit = ???
}
