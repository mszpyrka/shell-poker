package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for PokerHandFactory class. */
class  PokerHandFactoryTest extends FunSuite {


  test("Every attempt to make a PokerHand from cards that can't be form a proper poker hand should throw InvalidPokerHandException.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(Queen, Diamonds),
      Card(Ace, Clubs),
      Card(Two, Diamonds),
      Card(Two, Spades)
    )

    assertThrows[InvalidPokerHandException](HighCard(l1))


    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Ten, Diamonds),
      Card(Jack, Spades),
      Card(Jack, Hearts)
    )

    assertThrows[InvalidPokerHandException](HighCard(l2))
  }
}

