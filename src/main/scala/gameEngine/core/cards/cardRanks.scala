package main.scala.gameEngine.core.cards

/** Represents card's rank. */
object CardRank {

  /** Contains all possible Rank classes. */
  val ranks: List[CardRank] = List(
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace
  )

}


/** Represents ranks instances.
  *
  * @param strength actual rank's strength as Int value (Two has the lowest strength, Ace has the highest)
  */
sealed class CardRank(val strength: Int) {

  /** Regular less-than binary operator overload. */
  def <(other: CardRank): Boolean = strength < other.strength

  /** Regular greater-than binary operator overload. */
  def >(other: CardRank): Boolean = strength > other.strength
}


case object Two extends CardRank(2)
case object Three extends CardRank(3)
case object Four extends CardRank(4)
case object Five extends CardRank(5)
case object Six extends CardRank(6)
case object Seven extends CardRank(7)
case object Eight extends CardRank(8)
case object Nine extends CardRank(9)
case object Ten extends CardRank(10)
case object Jack extends CardRank(11)
case object Queen extends CardRank(12)
case object King extends CardRank(13)
case object Ace extends CardRank(14)

