package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.PokerTable

/** Represents the type of ending when all players in game went all-in before the river.
  * Final actions in this case include showing all in-game players' cards and dealing missing streets.
  *
  * @param table Table at which the game is played.
  */
class PlayersAllInEnding(val table: PokerTable) extends HandEndingHelper(table, PlayersAllIn) {

  override def proceedWithFinalActions(): Unit = ???

  override def distributePot(): Unit = ???
}
