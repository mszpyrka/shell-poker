package shellPoker.core.pokerHands

import shellPoker.core.cards.CardRank
import shellPoker.gameEngine.core.cards._

case object FourOfAKind extends PokerHandFactory(FourOfAKindRank) {

  /** Creates a new FourOfAKind instance. */
  override protected def makeHand(cards: List[Card]) = new FourOfAKind(cards)


  /** Tests if given cards list make a FourOfAKind hand.
    *
    * Checks if the list of ranks that appear exactly least four times in the cards list is not empty.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean =
    HandEvaluationHelper.getRanksByCount(cards, _ == 4).nonEmpty
}


/** Represents a FourOfAKind poker hand. */
case class FourOfAKind private(override val cards: List[Card]) extends PokerHand(FourOfAKindRank, cards) {

  /** Returns true if this.cards make a stronger FourOfAKind than other.cards.
    *
    * Firstly compares ranks of cards that make a FourOfAKind in both hands.
    * If those are the same, then the kickers decide which hand is better.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisQuadRank: CardRank = HandEvaluationHelper.getRanksByCount(this.cards, _ == 4).head
    val otherQuadRank: CardRank = HandEvaluationHelper.getRanksByCount(other.cards, _ == 4).head

    if (thisQuadRank > otherQuadRank)
      return true

    if (thisQuadRank < otherQuadRank)
      return false

    // In case of equally ranked pair - checks other cards.
    val thisKickers: List[Card] = this.cards.filter(_.rank != thisQuadRank)
    val otherKickers: List[Card] = other.cards.filter(_.rank != otherQuadRank)

    HandEvaluationHelper.compareKickers(thisKickers, otherKickers) > 0
  }
}