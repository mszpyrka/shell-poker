package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.table.Showdown

/** Represents the type of ending when all players in game went all-in before the river.
  * Final actions in this case include showing all in-game players' cards and dealing missing streets.
  *
  * @param gameState The state of the game when there is no further action possibility.
  */
class PlayersAllInEnding(val gameState: GameState) extends HandEndingHelper(gameState, PlayersAllIn) {

  /* Makes every player at the table show their cards and deals missing cards. */
  override def proceedWithFinalActions(): Unit = {

    val table = gameState.table
    table.playersInHand.foreach(_.showCards())
    while(table.dealer.status != Showdown)
      table.dealer.proceedWithAction()
  }
}
