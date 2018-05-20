package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards._

case object Straight extends PokerHandFactory(StraightRank) {

  /** Creates a new Straight instance. */
  override def makeHand(cards: List[Card]) = new Straight(cards)


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


/** Represents a pair poker hand. */
case class Straight private(override val cards: List[Card]) extends PokerHand(StraightRank, cards) {

  /** Returns true if this.cards make a stronger straight than other.cards.
    *
    * ???
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    ???
  }
}
