package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for FourOfAKind class. */
class FourOfAKindTest extends FunSuite {

  test("Certain cards should make at least a FourOfAKind hand") {

    val toCheck = List(TestHelper.pokerHands("FourOfAKind"),
                       TestHelper.betterPokerHands("FourOfAKind"),
                       TestHelper.worsePokerHands("FourOfAKind")
    )

    for(i <- toCheck) assert(FourOfAKind.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a FourOfAKind hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!FourOfAKind.isMadeUpOf(i))
  }

  test("Comparing different FourOfAKind hands.") {

    val hand = FourOfAKind(TestHelper.pokerHands("FourOfAKind"))
    val worseHand = FourOfAKind(TestHelper.worsePokerHands("FourOfAKind"))
    val betterHand =  FourOfAKind(TestHelper.betterPokerHands("FourOfAKind"))
    val equalHand = FourOfAKind(TestHelper.pokerHands("FourOfAKind"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make FourOfAKind from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "FourOfAKind") {
      assertThrows[InvalidPokerHandException](FourOfAKind(v))
    }
  }
 
}

