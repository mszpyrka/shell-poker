package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for FullHouse class. */
class FullHouseTest extends FunSuite {

  test("Certain cards should make at least a FullHouse hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("FullHouse"),
                       PokerHandTestHelper.betterPokerHands("FullHouse"),
                       PokerHandTestHelper.worsePokerHands("FullHouse"),
    )

    for(i <- toCheck) assert(FullHouse.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a FullHouse hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("RoyalFlush"),
                       PokerHandTestHelper.pokerHands("ThreeOfAKind")
    )

    for(i <- toCheck) assert(!FullHouse.isMadeUpOf(i))
  }

  test("Comparing different FullHouse hands.") {

    val hand = FullHouse(PokerHandTestHelper.pokerHands("FullHouse"))
    val worseHand = FullHouse(PokerHandTestHelper.worsePokerHands("FullHouse"))
    val betterHand =  FullHouse(PokerHandTestHelper.betterPokerHands("FullHouse"))
    val equalHand = FullHouse(PokerHandTestHelper.pokerHands("FullHouse"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make FullHouse from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "FullHouse") {
      assertThrows[InvalidPokerHandException](FullHouse(v))
    }
  }
 
}

