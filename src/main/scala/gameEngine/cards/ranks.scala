package main.scala.gameEngine.cards

/** Represents card's rank.
  *
  * @param strength actual rank's strength as Int value (Two has the lowest strength, Ace has the highest)
  */
sealed class Rank(val strength: Int, val abbreviation: String) {

  /** Regular less-than binary operator overload. */
  def <(other: Rank): Boolean = strength < other.strength

  /** Regular greater-than binary operator overload. */
  def >(other: Rank): Boolean = strength > other.strength
}


case object Two extends Rank(2, "2")
case object Three extends Rank(3, "3")
case object Four extends Rank(4, "4")
case object Five extends Rank(5, "5")
case object Six extends Rank(6, "6")
case object Seven extends Rank(7, "7")
case object Eight extends Rank(8, "8")
case object Nine extends Rank(9, "9")
case object Ten extends Rank(10, "T")
case object Jack extends Rank(11, "J")
case object Queen extends Rank(12, "Q")
case object King extends Rank(13, "K")
case object Ace extends Rank(14, "A")

