package gameEngine.pokerHands
import gameEngine.cards.Card

/** Used only for testing if given cards make at least high card hand. */
object HighCard extends PokerHandEvaluator {

  override def isMadeUpOf(cards: List[Card]): Boolean = true
}

/** Represents high card poker hand. */
class HighCard(override val cards: List[Card]) extends PokerHand(HighCardRank, cards) {

  /** Returns true if this.cards make stronger high card than other.cards.
    *
    * Sorts both hands' cards lists in descending order and compares their elements one by one
    * until a pair of differently ranked cards is found.
    */
  override def isStrongerWithinRank(other: PokerHand): Boolean = {

    val selfSortedCards = cards.sortWith(_.rank > _.rank)
    val otherSortedCards = other.cards.sortWith(_.rank > _.rank)

    for ((selfCard, otherCard) <- selfSortedCards zip otherSortedCards)
      if (selfCard.rank != otherCard.rank) return selfCard.rank > otherCard.rank

    false
  }
}
