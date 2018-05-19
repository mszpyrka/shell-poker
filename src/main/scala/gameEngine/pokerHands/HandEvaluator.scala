package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.Card

/** Used to tests if cards list make some particular poker hands
  * and to create instances of particular poker hands.
  */
trait HandEvaluator {

  /** Creates new PokerHand. */
  def apply(cards: List[Card]): PokerHand = {
    if (!this.isMadeUpOf(cards))
      throw new InvalidPokerHandException(this, cards)

    makeHand(cards)
  }

  /** Creates new PokerHand with particular HandRank and cards list. */
  def makeHand(cards: List[Card]): PokerHand

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean
}
