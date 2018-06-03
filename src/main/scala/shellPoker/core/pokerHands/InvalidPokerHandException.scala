package shellPoker.core.pokerHands

import shellPoker.core.cards._

/** Thrown when there is an attempt to create a PokerHand instance with somehow invalid cards. */
case class InvalidPokerHandException(message: String) extends Exception(message) {

  def this(message: String, evaluator: PokerHandFactory, cards: List[Card]) =
    this(evaluator.toString + " " + message + " " + cards.mkString(": "))
}
