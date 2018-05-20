package main.scala.gameEngine.core.pokerHands

/** Represents different poker hand ranks.
  *
  * @param strength hand's strength (high card has the lowest one, royal flush has the highest)
  */
sealed class HandRank(val strength: Int) {

  /** Regular less-than binary operator overload. */
  def <(other: HandRank): Boolean = strength < other.strength

  /** Regular greater-than binary operator overload. */
  def >(other: HandRank): Boolean = strength > other.strength
}


case object HighCardRank extends HandRank(0)
case object PairRank extends HandRank(1)
case object TwoPairRank extends HandRank(2)
case object ThreeOfAKindRank extends HandRank(3)
case object StraightRank extends HandRank(4)
case object FlushRank extends HandRank(5)
case object FullHouseRank extends HandRank(6)
case object FourOfAKindRank extends HandRank(7)
case object StraightFlushRank extends HandRank(8)
case object RoyalFlushRank extends HandRank(9)

