package shellPoker.gameEngine.core.pokerHands

import shellPoker.gameEngine.core.cards._

case object Flush extends PokerHandFactory(FlushRank) {

  /** Creates a new Flush instance. */
  override protected def makeHand(cards: List[Card]) = new Flush(cards)


  /** Tests if given cards list make a Flush hand. */
  override def isMadeUpOf(cards: List[Card]): Boolean = {
    HandEvaluationHelper.areSingleSuited(cards)
  }
}


/** Represents a Flush poker hand. */
case class Flush private(override val cards: List[Card]) extends PokerHand(FlushRank, cards) {

  /** Returns true if this.cards make a stronger Flush than other.cards.
    *
    * Evaluation as in HighCard poker hand
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = 
    HandEvaluationHelper.compareKickers(this.cards, other.cards) > 0
  
}
