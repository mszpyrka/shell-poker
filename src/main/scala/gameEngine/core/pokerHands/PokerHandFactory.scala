package main.scala.gameEngine.core.pokerHands

import main.scala.gameEngine.core.cards.{Card, CardRank}

object PokerHandFactory {

  /** All PokerHandFactory subclasses. */
  val handFactories: List[PokerHandFactory with HandEvaluator] = List(
    HighCard,
    Pair,
    TwoPair,
    ThreeOfAKind,
    Straight
  )


  /** Makes the best recognized poker hand made up of cards list.
    *
    * @param cards List[Card] to make the hand up of
    * @return proper PokerHand subclass
    *
    * Iterates through all possible poker hands sorted by their ranks in descending order and returns the first one that can be made up of the cards.
    *
    * InvalidPokerHandException is thrown in case the cards make none of the hands present in handFactories list.
    * This should never occur as every 5 cards make at least high card.
    */
  def makeBestHand(cards: List[Card]): PokerHand = {

    for (hand <- handFactories.sortWith(_.handRank > _.handRank)) if (hand.isMadeUpOf(cards)) return hand(cards)

    throw InvalidPokerHandException("Could not create any hand from " + cards.mkString(", "))
  }



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

    if (PokerHandFactory.handFactories.filter(_.handRank > this.handRank).exists(_.isMadeUpOf(cards)))
      throw new InvalidPokerHandException("is not the best hand made up of", this, cards)

    makeHand(cards)
  }


  /** Creates new PokerHand with particular HandRank and cards list. */
  protected def makeHand(cards: List[Card]): PokerHand
}
