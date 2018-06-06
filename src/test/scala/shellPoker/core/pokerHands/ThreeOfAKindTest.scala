package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for ThreeOfAKind class. */
class ThreeOfAKindTest extends FunSuite {

  test("Certain cards should make at least a ThreeOfAKind hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("ThreeOfAKind"),
                       PokerHandTestHelper.betterPokerHands("ThreeOfAKind"),
                       PokerHandTestHelper.worsePokerHands("ThreeOfAKind"),
                       PokerHandTestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(ThreeOfAKind.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a ThreeOfAKind hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!ThreeOfAKind.isMadeUpOf(i))
  }

  test("Comparing different ThreeOfAKind hands.") {

    val hand = ThreeOfAKind(PokerHandTestHelper.pokerHands("ThreeOfAKind"))
    val worseHand = ThreeOfAKind(PokerHandTestHelper.worsePokerHands("ThreeOfAKind"))
    val betterHand =  ThreeOfAKind(PokerHandTestHelper.betterPokerHands("ThreeOfAKind"))
    val equalHand = ThreeOfAKind(PokerHandTestHelper.pokerHands("ThreeOfAKind"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make ThreeOfAKind from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "ThreeOfAKind") {
      assertThrows[InvalidPokerHandException](ThreeOfAKind(v))
    }
  }
 
}

