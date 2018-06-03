package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for TwoPair class. */
class TwoPairTest extends FunSuite {

  test("Certain cards should make at least two pair hand"){

    val l1 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Ace, Clubs),
      Card(Ace, Diamonds),
      Card(Three, Spades)
    )

    assert(TwoPair.isMadeUpOf(l1))

    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(King, Clubs),
      Card(Queen, Diamonds),
      Card(Jack, Spades)
    )

    assert(TwoPair.isMadeUpOf(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(King, Spades)
    )
  }

  test("Certain cards should NOT make a two pair hand"){
     val l1 = List(
      Card(Queen, Diamonds),
      Card(Jack, Spades),
      Card(Ace, Clubs),
      Card(King, Hearts),
      Card(Three, Spades)
    )

    assert(!TwoPair.isMadeUpOf(l1))

    val l2 = List(
      Card(Queen, Diamonds),
      Card(Jack, Spades),
      Card(Ace, Clubs),
      Card(King, Hearts),
      Card(Ten, Spades)
    )

    assert(!TwoPair.isMadeUpOf(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(Ten, Spades)
    )

    assert(!TwoPair.isMadeUpOf(l3))

    val l4 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(Queen, Hearts),
      Card(Ten, Spades)
    )

    assert(!TwoPair.isMadeUpOf(l4))
  }

  test("Every attempt to make TwoPair from cards that make better hand should throw InvalidPokerHandException.") {

    val l1 = List(
      Card(King, Diamonds),
      Card(King, Spades),
      Card(King, Clubs),
      Card(Two, Diamonds),
      Card(Two, Spades)
    )

    assertThrows[InvalidPokerHandException](TwoPair(l1))


    val l2 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Ace, Clubs),
      Card(Queen, Diamonds),
      Card(Jack, Spades)
    )

    assertThrows[InvalidPokerHandException](TwoPair(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(King, Diamonds),
      Card(Ace, Spades),
      Card(Two, Diamonds),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](TwoPair(l3))

    val l4 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Hearts),
      Card(Queen, Clubs),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](TwoPair(l4))

    val l5 = List(
      Card(Seven, Diamonds),
      Card(Six, Spades),
      Card(Six, Diamonds),
      Card(Four, Diamonds),
      Card(Four, Diamonds)
    )

    assertThrows[InvalidPokerHandException](TwoPair(l5))
  }

}