package shellPoker.core.pokerHands

import shellPoker.core.cards.CardRank
import shellPoker.gameEngine.core.cards._

case object FullHouse extends PokerHandFactory(FullHouseRank) {

  /** Creates a new FullHouse instance. */
  override protected def makeHand(cards: List[Card]) = new FullHouse(cards)


  /** Tests if given cards list make a FullHouse poker hand. */
  override def isMadeUpOf(cards: List[Card]): Boolean = {
    
    val pairList: List[CardRank] = HandEvaluationHelper.getRanksByCount(cards, _ == 2)
    val setList: List[CardRank] = HandEvaluationHelper.getRanksByCount(cards, _ == 3)

    pairList.nonEmpty && setList.nonEmpty
  }
}


/** Represents a FullHouse poker hand. */
case class FullHouse private(override val cards: List[Card]) extends PokerHand(FullHouseRank, cards) {

  /** Returns true if this.cards make a stronger FullHouse than other.cards.
    *
    * First compares set ranks and if they are equal, compares pair ranks.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {
    val thisSetRank: CardRank = HandEvaluationHelper.getRanksByCount(this.cards, _ == 3).head
    val otherSetRank: CardRank = HandEvaluationHelper.getRanksByCount(other.cards, _ == 3).head

    val thisPairRank: CardRank = HandEvaluationHelper.getRanksByCount(this.cards, _ == 2).head
    val otherPairRank: CardRank = HandEvaluationHelper.getRanksByCount(other.cards, _ == 2).head

    if (thisSetRank > otherSetRank)
      return true

    if (thisSetRank < otherSetRank)
      return false

    if (thisPairRank > otherPairRank)
      return true

    if (thisPairRank < otherPairRank)
      return false

    return false
  }
}
