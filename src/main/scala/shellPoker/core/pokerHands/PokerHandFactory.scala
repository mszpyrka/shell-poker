package shellPoker.core.pokerHands

import shellPoker.core.cards._

object PokerHandFactory {

  /** All PokerHandFactory subclasses. */
  val handFactories: List[PokerHandFactory with HandEvaluator] = List(
    HighCard,
    Pair,
    TwoPair,
    ThreeOfAKind,
    Straight,
    Flush,
    FullHouse,
    FourOfAKind,
    StraightFlush,
    RoyalFlush
  )


  /** Picks the best 5 cards out of cards list and returns proper poker hand.
    *
    * @param cards List[Card] to choose from
    * @return best possible PokerHand made up of five cards from the list
    */
  def pickBestHand(cards: List[Card]): PokerHand = {

    def getBetterHand(hand1: PokerHand, hand2: PokerHand): PokerHand = if (hand1.isStrongerThan(hand2)) hand1 else hand2
    cards.combinations(5).map(makeProperHand).reduce(getBetterHand)
  }


  /** Makes the best recognized poker hand made up of 5-card list.
    *
    * @param cards List[Card] to make the hand up of
    * @return proper PokerHand subclass
    *
    * Iterates through all possible poker hands sorted by their ranks in descending order and returns the first one that can be made up of the cards.
    *
    * InvalidPokerHandException is thrown in case the cards make none of the hands present in handFactories list.
    * This should never occur as every 5 cards make at least high card.
    */
  private def makeProperHand(cards: List[Card]): PokerHand = {

    for (hand <- handFactories.sortWith(_.handRank > _.handRank))
      if (hand.isMadeUpOf(cards)) return hand(cards)

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
    *  - the cards list does not contain exactly 5 cards
    *  - some cards appear more than once it the cards list
    *  - the cards actually do not make given hand
    *  - a better hand can be made up of the cards
    */
  def apply(cards: List[Card]): PokerHand = {

    if (cards.length != 5)
      throw new InvalidPokerHandException("poker hand must consist of exactly five cards")

    if (cards.groupBy(identity).values.exists(_.length > 1))
      throw new InvalidPokerHandException(
        "repetitive cards in hand " + cards.mkString(", "))

    if (!this.isMadeUpOf(cards))
      throw new InvalidPokerHandException(
        "cannot be made up of", this, cards)

    if (PokerHandFactory.handFactories.
        filter(_.handRank > this.handRank).
        exists(_.isMadeUpOf(cards)))
      throw new InvalidPokerHandException(
        "is not the best hand made up of", this, cards)

    makeHand(cards)
  }


  /** Creates new PokerHand with particular HandRank and cards list. */
  protected def makeHand(cards: List[Card]): PokerHand
}
