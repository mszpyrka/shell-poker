package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for TwoPair class. */
class TwoPairTest extends FunSuite {

  test("Certain cards should make at least a TwoPair hand") {

    val toCheck = List(TestHelper.pokerHands("TwoPair"),
                       TestHelper.betterPokerHands("TwoPair"),
                       TestHelper.worsePokerHands("TwoPair"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(TwoPair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a TwoPair hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("ThreeOfAKind")
    )

    for(i <- toCheck) assert(!TwoPair.isMadeUpOf(i))
  }

  test("Comparing different TwoPair hands.") {

    val hand = TwoPair(TestHelper.pokerHands("TwoPair"))
    val worseHand = TwoPair(TestHelper.worsePokerHands("TwoPair"))
    val betterHand =  TwoPair(TestHelper.betterPokerHands("TwoPair"))
    val equalHand = TwoPair(TestHelper.pokerHands("TwoPair"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make TwoPair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "TwoPair") {
      assertThrows[InvalidPokerHandException](TwoPair(v))
    }
  }
 
}

