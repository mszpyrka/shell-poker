package main.scala.gameEngine.pokerHands

import main.scala.gameEngine.cards.{Card, CardRank}

case object Pair extends HandEvaluator {

  override val handRank: HandRank = PairRank


  /** Creates a new Pair instance. */
  override def makeHand(cards: List[Card]) = new Pair(cards)


  /** Tests if given cards list make a pair hand.
    *
    * Sorts all cards by their rank and checks if any two consecutive cards in the sorted list have the same rank.
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = {

    val sortedCards = cards.sortWith(_.rank < _.rank)
    val zippedCards = sortedCards zip sortedCards.drop(1)
    zippedCards.exists { case (a, b) => a.rank == b.rank }
  }


  /** Returns the rank of the pair contained in this.cards. */
  private def getPairRank(cards: List[Card]): CardRank = {

    // Counts all rank appearances in this.cards.
    val countRank = (r: CardRank) => cards.count(_.rank == r)

    // Gets all ranks that appear more than once in this.cards (there will be always exactly one such value).
    val pairRanks: List[CardRank] = CardRank.ranks.filter(countRank(_) > 1)

    pairRanks.head
  }
}

/** Represents a pair poker hand. */
case class Pair private(override val cards: List[Card]) extends PokerHand(PairRank, cards) {

  /** Returns true if this.cards make a stronger pair than other.cards.
    *
    * Firstly compares ranks of pair in both hand.
    * If those are the same, then the kickers decide which hand is better.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisPairRank: CardRank = Pair.getPairRank(this.cards)
    val otherPairRank: CardRank = Pair.getPairRank(other.cards)

    if (thisPairRank > otherPairRank)
      return true

    if (thisPairRank < otherPairRank)
      return false

    // In case of equally ranked pair - checks other cards.
    val thisKickers: List[Card] = this.cards.filter(_.rank != thisPairRank)
    val otherKickers: List[Card] = other.cards.filter(_.rank != otherPairRank)

    HighCard(thisKickers).isStrongerThan(HighCard(otherKickers))
  }
}

