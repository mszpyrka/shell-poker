package main.scala.gameEngine.core.cards

/** Represents card's suit. */
object Suit {

  /** All possible suits. */
  val suits: List[Suit] = List(
    Clubs,
    Diamonds,
    Hearts,
    Spades
  )
}

/** Represents particular suits. */
sealed class Suit

case object Clubs extends Suit
case object Diamonds extends Suit
case object Hearts extends Suit
case object Spades extends Suit

