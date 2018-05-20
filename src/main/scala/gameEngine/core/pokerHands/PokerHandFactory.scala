package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards.{Card, CardRank}

object PokerHandFactory {

  val evaluators: List[PokerHandFactory with HandEvaluator] = List(
    HighCard,
    Pair,
    TwoPair,
    ThreeOfAKind,
    Straight
  )



}

/** Used to tests if cards list make some particular poker hands
  * and to create instances of particular poker hands.
  */
protected abstract class PokerHandFactory (val handRank: HandRank) extends HandEvaluator {

  /** Creates new PokerHand.
    *
    * Throws InvalidPokerHandException in any of those cases:
    *  - the cards actually do not make given hand
    *  - a better hand can be made up of the cards
    */
  def apply(cards: List[Card]): PokerHand = {

    if(cards.length != 5)
      throw new InvalidPokerHandException("poker hand must consist of exactly five cards")

    if (!this.isMadeUpOf(cards))
      throw new InvalidPokerHandException("cannot be made up of", this, cards)

    if (PokerHandFactory.evaluators.filter(_.handRank > this.handRank).exists(_.isMadeUpOf(cards)))
      throw new InvalidPokerHandException("is not the best hand made up of", this, cards)

    makeHand(cards)
  }


  /** Creates new PokerHand with particular HandRank and cards list. */
  protected def makeHand(cards: List[Card]): PokerHand
}
