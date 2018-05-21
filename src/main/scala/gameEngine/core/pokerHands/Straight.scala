package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards._

case object Straight extends PokerHandFactory(StraightRank) {

  /** Creates a new Straight instance. */
  override protected def makeHand(cards: List[Card]) = new Straight(cards)


  /** Tests if given cards list make a straight hand.
    *
    * Firstly checks if cards make an ace-to-five straight.
    * If not, checks if cards contain five consecutive ranks.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = {

    val sortedRanks: List[CardRank] = cards.map(_.rank).sortWith(_ < _)

    if (sortedRanks == List(Two, Three, Four, Five, Ace))
      return true

    (sortedRanks zip sortedRanks.drop(1)).forall { case (a, b) => a.strength + 1 == b.strength }
  }
}


/** Represents a straight poker hand. */
case class Straight private(override val cards: List[Card]) extends PokerHand(StraightRank, cards) {

  /** Returns true if this.cards make a stronger straight than other.cards.
    *
    * Firstly checks if this.cards make ace-to-five straight (if so, returns false as there is no lower straight).
    * Otherwise checks if other.cards make ace-to-five straight, in which case returns true.
    * Finally compares the top ranked cards in both hands.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisSortedRanks: List[CardRank] = this.cards.map(_.rank).sortWith(_ < _)
    val otherSortedRanks: List[CardRank] = other.cards.map(_.rank).sortWith(_ < _)

    if (thisSortedRanks == List(Two, Three, Four, Five, Ace))
      return false

    if (otherSortedRanks == List(Two, Three, Four, Five, Ace))
      return true

    thisSortedRanks.last > otherSortedRanks.last
  }
}
