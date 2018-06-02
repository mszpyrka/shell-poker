package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for HighCard class. */
class HighCardTest extends FunSuite {

  test("Every 5 cards make at least high card hand.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Two, Diamonds),
      Card(Three, Spades)
    )

    assert(HighCard.isMadeUpOf(l1))

    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Ten, Diamonds),
      Card(Jack, Spades)
    )

    assert(HighCard.isMadeUpOf(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(Queen, Hearts),
      Card(Three, Spades)
    )

    assert(HighCard.isMadeUpOf(l3))
  }

  test("Comparing different high card hands.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Ten, Diamonds),
      Card(Three, Spades)
    )
    val hand1 = HighCard(l1)

    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Ten, Diamonds),
      Card(Nine, Spades)
    )
    val hand2 = HighCard(l2)

    val l3 = List(
      Card(Ace, Diamonds),
      Card(King, Spades),
      Card(Queen, Clubs),
      Card(Jack, Hearts),
      Card(Three, Spades)
    )
    val hand3 = HighCard(l3)

    val l4 = List(
      Card(Ace, Diamonds),
      Card(King, Diamonds),
      Card(Queen, Diamonds),
      Card(Jack, Hearts),
      Card(Three, Diamonds)
    )
    val hand4 = HighCard(l4)

    assert(hand1.isWeakerThan(hand2))
    assert(hand3.isStrongerThan(hand2))
    assert(hand4.isEquallyStrongAs(hand3))
  }


  test("Every attempt to make HighCard from cards that make better hand should throw InvalidPokerHandException.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
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
      Card(Jack, Spades)
    )

    assertThrows[InvalidPokerHandException](HighCard(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(King, Diamonds),
      Card(Ace, Diamonds),
      Card(Two, Diamonds),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](HighCard(l3))
  }
}

