package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Pair class. */
class PairTest extends FunSuite {

  test("Certain cards should make at least a Pair hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Pair"),
                       TestHelper.worsePokerHands("Pair"),
                       TestHelper.pokerHands("ThreeOfAKind"),
                       TestHelper.pokerHands("FourOfAKind"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(Pair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Pair hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.pokerHands("Flush"),
                       TestHelper.pokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!Pair.isMadeUpOf(i))
  }

  test("Comparing different Pair hands.") {

    val hand = Pair(TestHelper.pokerHands("Pair"))
    val worseHand = Pair(TestHelper.worsePokerHands("Pair"))
    val betterHand =  Pair(TestHelper.betterPokerHands("Pair"))
    val equalHand = Pair(TestHelper.pokerHands("Pair"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make Pair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "Pair") {
      assertThrows[InvalidPokerHandException](Pair(v))
    }
  }
 
}

