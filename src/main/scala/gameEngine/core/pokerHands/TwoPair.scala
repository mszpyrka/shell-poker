package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards.{Card, CardRank}

case object TwoPair extends PokerHandFactory(TwoPairRank) {

  /** Creates a new TwoPair instance. */
  override protected def makeHand(cards: List[Card]) = new TwoPair(cards)


  /** Tests if given cards list make a two pair hand.
    *
    * Checks if the list of ranks that appear more than once in the cards list has the length of 2.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = HandEvaluationHelper.getRanksByCount(cards, _ > 1).length == 2
}


/** Represents a two pair poker hand. */
case class TwoPair private(override val cards: List[Card]) extends PokerHand(TwoPairRank, cards) {

  /** Returns true if this.cards make stronger two pairs than other.cards.
    *
    * Firstly compares ranks of pairs in both hand.
    * If those are the same, then the kickers decide which hand is better.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisPairRanks: List[CardRank] = HandEvaluationHelper.getRanksByCount(this.cards, _ == 2).sortWith(_ > _)
    val otherPairRanks: List[CardRank] = HandEvaluationHelper.getRanksByCount(other.cards, _ == 2).sortWith(_ > _)

    if (thisPairRanks(0) > otherPairRanks(0) || (thisPairRanks(0) == otherPairRanks(0) && thisPairRanks(1) > otherPairRanks(1)))
      return true

    if (otherPairRanks(0) > thisPairRanks(0) || (otherPairRanks(0) == thisPairRanks(0) && otherPairRanks(1) > thisPairRanks(1)))
      return false

    // In case of equally ranked pairs - checks the kickers.
    val thisKickerRank = HandEvaluationHelper.getRanksByCount(this.cards, _ == 1).head
    val otherKickerRank = HandEvaluationHelper.getRanksByCount(other.cards, _ == 1).head

    thisKickerRank > otherKickerRank
  }
}
