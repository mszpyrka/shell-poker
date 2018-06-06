package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for TwoPair class. */
class TwoPairTest extends FunSuite {

  test("Certain cards should make at least a TwoPair hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("TwoPair"),
                       PokerHandTestHelper.betterPokerHands("TwoPair"),
                       PokerHandTestHelper.worsePokerHands("TwoPair"),
                       PokerHandTestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(TwoPair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a TwoPair hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("ThreeOfAKind")
    )

    for(i <- toCheck) assert(!TwoPair.isMadeUpOf(i))
  }

  test("Comparing different TwoPair hands.") {

    val hand = TwoPair(PokerHandTestHelper.pokerHands("TwoPair"))
    val worseHand = TwoPair(PokerHandTestHelper.worsePokerHands("TwoPair"))
    val betterHand =  TwoPair(PokerHandTestHelper.betterPokerHands("TwoPair"))
    val equalHand = TwoPair(PokerHandTestHelper.pokerHands("TwoPair"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make TwoPair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "TwoPair") {
      assertThrows[InvalidPokerHandException](TwoPair(v))
    }
  }
 
}

