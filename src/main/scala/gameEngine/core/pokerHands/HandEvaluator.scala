package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards.Card

/** Used by classes which evaluate poker hands. */
trait HandEvaluator {

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean

}
