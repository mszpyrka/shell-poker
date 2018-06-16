package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.player.Player

/** Represents single player's results after one hand (amount of chips won by the player).
  *
  * @param player Player to whom the results are related.
  */
class SinglePlayerHandResults(val player: Player) {

  private var _chipsWon = 0

  def chipsWon: Int = _chipsWon

  def addChips(amount: Int): Unit = _chipsWon += amount
}
