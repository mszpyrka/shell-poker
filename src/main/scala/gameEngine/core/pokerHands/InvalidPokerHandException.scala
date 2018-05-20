package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards.Card

/** Thrown when there is an attempt to create a PokerHand instance with cards that do not make that hand or make a better hand. */
case class InvalidPokerHandException(message: String) extends Exception(message) {

  def this(message: String, evaluator: PokerHandFactory, cards: List[Card]) = this(evaluator.toString + " " + message + " " + cards.mkString(": "))
}
