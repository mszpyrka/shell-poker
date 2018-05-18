package gameEngine.cards

/** Represents card's rank.
  *
  * @param strength actual rank's strength as Int value (Two has the lowest strength, Ace has the highest)
  */
sealed class Rank(val strength: Int) {

  /** Regular less-than binary operator overload. */
  def <(other: Rank): Boolean = strength < other.strength

  /** Regular greater-than binary operator overload. */
  def >(other: Rank): Boolean = strength > other.strength
}


case object Two extends Rank(2)
case object Three extends Rank(3)
case object Four extends Rank(4)
case object Five extends Rank(5)
case object Six extends Rank(6)
case object Seven extends Rank(7)
case object Eight extends Rank(8)
case object Nine extends Rank(9)
case object Ten extends Rank(10)
case object Jack extends Rank(11)
case object Queen extends Rank(12)
case object King extends Rank(13)
case object Ace extends Rank(14)

