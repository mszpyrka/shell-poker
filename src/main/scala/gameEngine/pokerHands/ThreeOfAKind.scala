package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.{Card, CardRank}

case object ThreeOfAKind extends PokerHandFactory(ThreeOfAKindRank) {

  /** Creates a new ThreeOfAKind instance. */
  override def makeHand(cards: List[Card]) = new ThreeOfAKind(cards)


  /** Tests if given cards list make a three of a kind hand.
    *
    * Checks if the list of ranks that appear at least three times in the cards list is not empty.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = PokerHandFactory.countAndFilterRanks(cards, _ >= 3).nonEmpty
}


/** Represents a three of a kind poker hand. */
case class ThreeOfAKind private(override val cards: List[Card]) extends PokerHand(ThreeOfAKindRank, cards) {

  /** Returns true if this.cards make a stronger set than other.cards.
    *
    * Firstly compares ranks of sets in both hand.
    * If those are the same, then the kickers decide which hand is better.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisSetRank: CardRank = PokerHandFactory.countAndFilterRanks(this.cards, _ == 3).head
    val otherSetRank: CardRank = PokerHandFactory.countAndFilterRanks(other.cards, _ == 3).head

    if (thisSetRank > otherSetRank)
      return true

    if (thisSetRank < otherSetRank)
      return false

    // In case of equally ranked pair - checks other cards.
    val thisKickers: List[Card] = this.cards.filter(_.rank != thisSetRank)
    val otherKickers: List[Card] = other.cards.filter(_.rank != otherSetRank)

    PokerHandFactory.compareKickers(thisKickers, otherKickers) > 0
  }
}