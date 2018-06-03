package shellPoker.core.pokerHands

import shellPoker.core.cards._

case object RoyalFlush extends PokerHandFactory(RoyalFlushRank) {

  /** Creates a new RoyalFlush instance. */
  override protected def makeHand(cards: List[Card]) = new RoyalFlush(cards)


  /** Tests if given cards list make a RoyalFlush hand.
    *
    * Checks if cards make an Ten-to-Ace same suit sequence.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = {

    val sortedRanks: List[CardRank] = cards.map(_.rank).sortWith(_ < _)

    if (sortedRanks == List(Ten, Jack, Queen, King, Ace))
      return HandEvaluationHelper.areSingleSuited(cards)

    false
  }
}


/** Represents a RoyalFlush poker hand. */
case class RoyalFlush private(override val cards: List[Card]) extends PokerHand(RoyalFlushRank, cards) {

  /** Returns true if this.cards make a stronger RoyalFlush than other.cards.
    *
    * Actually RoyalFlush is always equal to any other RoyalFlush
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = false

}
