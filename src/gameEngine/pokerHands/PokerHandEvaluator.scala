package gameEngine.pokerHands

import gameEngine.cards.Card

/** Used to tests if cards list make some particular poker hands. */
trait PokerHandEvaluator {

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean
}
