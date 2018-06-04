package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for ThreeOfAKind class. */
class ThreeOfAKindTest extends FunSuite {

  test("Certain cards should make at least a ThreeOfAKind hand") {

    val toCheck = List(TestHelper.pokerHands("ThreeOfAKind"),
                       TestHelper.betterPokerHands("ThreeOfAKind"),
                       TestHelper.worsePokerHands("ThreeOfAKind"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(ThreeOfAKind.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a ThreeOfAKind hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!ThreeOfAKind.isMadeUpOf(i))
  }

  test("Comparing different ThreeOfAKind hands.") {

    val hand = ThreeOfAKind(TestHelper.pokerHands("ThreeOfAKind"))
    val worseHand = ThreeOfAKind(TestHelper.worsePokerHands("ThreeOfAKind"))
    val betterHand =  ThreeOfAKind(TestHelper.betterPokerHands("ThreeOfAKind"))
    val equalHand = ThreeOfAKind(TestHelper.pokerHands("ThreeOfAKind"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make ThreeOfAKind from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "ThreeOfAKind") {
      assertThrows[InvalidPokerHandException](ThreeOfAKind(v))
    }
  }
 
}

