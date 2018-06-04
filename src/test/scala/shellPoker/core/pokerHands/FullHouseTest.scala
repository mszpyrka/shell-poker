package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for FullHouse class. */
class FullHouseTest extends FunSuite {

  test("Certain cards should make at least a FullHouse hand") {

    val toCheck = List(TestHelper.pokerHands("FullHouse"),
                       TestHelper.betterPokerHands("FullHouse"),
                       TestHelper.worsePokerHands("FullHouse"),
    )

    for(i <- toCheck) assert(FullHouse.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a FullHouse hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush"),
                       TestHelper.pokerHands("ThreeOfAKind")
    )

    for(i <- toCheck) assert(!FullHouse.isMadeUpOf(i))
  }

  test("Comparing different FullHouse hands.") {

    val hand = FullHouse(TestHelper.pokerHands("FullHouse"))
    val worseHand = FullHouse(TestHelper.worsePokerHands("FullHouse"))
    val betterHand =  FullHouse(TestHelper.betterPokerHands("FullHouse"))
    val equalHand = FullHouse(TestHelper.pokerHands("FullHouse"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make FullHouse from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "FullHouse") {
      assertThrows[InvalidPokerHandException](FullHouse(v))
    }
  }
 
}

