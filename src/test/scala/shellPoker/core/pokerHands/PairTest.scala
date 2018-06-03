package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Pair class. */
class PairTest extends FunSuite {

  test("Certain cards should make at least pair hand"){

    val l1 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Ace, Clubs),
      Card(Two, Diamonds),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l1))

    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(King, Clubs),
      Card(Ten, Diamonds),
      Card(Jack, Spades)
    )

    assert(Pair.isMadeUpOf(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l3))

    val l4 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(Queen, Hearts),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l4))

    val l5 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(King, Spades)
    )

    assert(Pair.isMadeUpOf(l5))
  }

  test("Certain cards should NOT make a pair hand"){
     val l5 = List(
      Card(Queen, Diamonds),
      Card(Jack, Spades),
      Card(Ace, Clubs),
      Card(King, Hearts),
      Card(Three, Spades)
    )

    assert(!Pair.isMadeUpOf(l5))
  }

}