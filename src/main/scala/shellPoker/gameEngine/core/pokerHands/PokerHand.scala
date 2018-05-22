package shellPoker.gameEngine.core.pokerHands

import shellPoker.gameEngine.core.cards.Card


/** Represents some particular poker hand.
  *
  * @param rank the rank of hand
  * @param cards cards of which the hand is made up
  */
abstract class PokerHand(val rank: HandRank, val cards: List[Card]) {

  /** Tests if this.cards make weaker poker hand than other.cards. */
  def isWeakerThan(other: PokerHand): Boolean = {

    if (rank < other.rank) true
    else if (rank > other.rank) false
    else !isStrongerWithinRank(other)
  }

  /** Tests if this.cards make stronger poker hand than other.cards. */
  def isStrongerThan(other: PokerHand): Boolean = {

    if (rank > other.rank) true
    else if (rank < other.rank) false
    else isStrongerWithinRank(other)
  }

  /** Tests if this.cards make an equally strong hand as other.cards within the same poker hand rank */
  def isEquallyStrongAs(other: PokerHand): Boolean =
    !this.isStrongerThan(other) &&
    !other.isStrongerThan(this)

  /** Tests if this.cards make a stronger hand than other.cards within the same poker hand rank. */
  protected def isStrongerWithinRank(other: PokerHand): Boolean
}
