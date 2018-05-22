package shellPoker.gameEngine.core.pokerHands

import shellPoker.gameEngine.core.cards.Card

/** Used by classes which evaluate poker hands. */
trait HandEvaluator {

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean

}
