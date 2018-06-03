package shellPoker.core.pokerHands

import shellPoker.core.cards.Card


case object HighCard extends PokerHandFactory(HighCardRank) {

  /** Creates a new HighCard instance. */
  override protected def makeHand(cards: List[Card]) = new HighCard(cards)

  /** Tests if given cards list make a high card hand.
    *
    * Actually there is no possibility that the cards do not make a high card hand.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = true
}

/** Represents a high card poker hand. */
case class HighCard private(override val cards: List[Card]) extends PokerHand(HighCardRank, cards) {

  /** Returns true if this.cards make a stronger high card than other.cards.
    *
    * Sorts both hands' cards lists in descending order and compares their elements one by one
    * until a pair of differently ranked cards is found.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean =
    HandEvaluationHelper.compareKickers(this.cards, other.cards) > 0
}
