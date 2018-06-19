package shellPoker.gameEngine.handEnding

import shellPoker.core.cards.Card
import shellPoker.core.pokerHands.{PokerHand, PokerHandFactory}
import shellPoker.gameEngine.player.Player

/** Represents single player's results after one hand (amount of chips won by the player).
  *
  * @param player Player to whom the results are related.
  */
class SinglePlayerHandResults(val player: Player, communityCards: List[Card]) {

  private var _chipsWon = 0

  def chipsWon: Int = _chipsWon

  def hand: PokerHand = {

    val (c1, c2) = player.holeCards
    PokerHandFactory.pickBestHand(communityCards ++ List(c1, c2))
  }

  def addChips(amount: Int): Unit = _chipsWon += amount
}
