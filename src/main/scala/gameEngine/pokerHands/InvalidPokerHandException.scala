package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.Card

/** Thrown when there is an attempt to create a PokerHand instance with cards that do not make that hand. */
case class InvalidPokerHandException(message: String) extends Exception(message) {

  def this(evaluator: HandEvaluator, cards: List[Card]) = this(
    evaluator.toString +
      " cannot be made up of given cards: " +
      cards.mkString(": ")
  )
}
