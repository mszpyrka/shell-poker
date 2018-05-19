package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.Card

/** Used to tests if cards list make some particular poker hands
  * and to create instances of particular poker hands.
  */
trait HandEvaluator {

  /** The rank of a hand that the evaluator is bounded with. */
  val handRank: HandRank

  /** Creates new PokerHand.
    *
    * Throws InvalidPokerHandException in any of those cases:
    *  - the cards actually do not make given hand
    *  - a better hand can be made up of the cards
    */
  def apply(cards: List[Card]): PokerHand = {
    if (!this.isMadeUpOf(cards))
      throw new InvalidPokerHandException("cannot be made up of", this, cards)

    if (PokerHandFactory.evaluators.filter(_.handRank > this.handRank).exists(_.isMadeUpOf(cards)))
      throw new InvalidPokerHandException("is not the best hand made up of", this, cards)

    makeHand(cards)
  }

  /** Creates new PokerHand with particular HandRank and cards list. */
  def makeHand(cards: List[Card]): PokerHand

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean
}
