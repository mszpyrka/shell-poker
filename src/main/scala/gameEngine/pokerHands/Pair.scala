package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.{Card, CardRank}

case object Pair extends PokerHandFactory(PairRank) {

  /** Creates a new Pair instance. */
  override def makeHand(cards: List[Card]) = new Pair(cards)


  /** Tests if given cards list make a pair hand.
    *
    * Checks if the list of ranks that appear more than once in the cards list is not empty.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = PokerHandFactory.countAndFilterRanks(cards, _ > 1).nonEmpty
}


/** Represents a pair poker hand. */
case class Pair private(override val cards: List[Card]) extends PokerHand(PairRank, cards) {

  /** Returns true if this.cards make a stronger pair than other.cards.
    *
    * Firstly compares ranks of pair in both hand.
    * If those are the same, then the kickers decide which hand is better.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisPairRank: CardRank = PokerHandFactory.countAndFilterRanks(this.cards, _ == 2).head
    val otherPairRank: CardRank = PokerHandFactory.countAndFilterRanks(other.cards, _ == 2).head

    if (thisPairRank > otherPairRank)
      return true

    if (thisPairRank < otherPairRank)
      return false

    // In case of equally ranked pair - checks other cards.
    val thisKickers: List[Card] = this.cards.filter(_.rank != thisPairRank)
    val otherKickers: List[Card] = other.cards.filter(_.rank != otherPairRank)

    PokerHandFactory.compareKickers(thisKickers, otherKickers) > 0
  }
}

