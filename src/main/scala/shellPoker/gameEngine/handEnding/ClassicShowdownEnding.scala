package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.PokerTable

/** Represents the type of ending when at least two players did not fold on the river.
  * Final actions in this case contain determining which players show / muck their cards.
  *
  * @param table Table at which the game is played.
  */
class ClassicShowdownEnding(val table: PokerTable) extends HandEndingHelper(table, ClassicShowdown) {

  override def proceedWithFinalActions(): Unit = ???

  override def distributePot(): Unit = ???
}
