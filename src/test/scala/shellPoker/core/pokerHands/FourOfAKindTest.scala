package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for FourOfAKind class. */
class FourOfAKindTest extends FunSuite {

  test("Certain cards should make at least a FourOfAKind hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("FourOfAKind"),
                       PokerHandTestHelper.betterPokerHands("FourOfAKind"),
                       PokerHandTestHelper.worsePokerHands("FourOfAKind")
    )

    for(i <- toCheck) assert(FourOfAKind.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a FourOfAKind hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("HighCard"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!FourOfAKind.isMadeUpOf(i))
  }

  test("Comparing different FourOfAKind hands.") {

    val hand = FourOfAKind(PokerHandTestHelper.pokerHands("FourOfAKind"))
    val worseHand = FourOfAKind(PokerHandTestHelper.worsePokerHands("FourOfAKind"))
    val betterHand =  FourOfAKind(PokerHandTestHelper.betterPokerHands("FourOfAKind"))
    val equalHand = FourOfAKind(PokerHandTestHelper.pokerHands("FourOfAKind"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make FourOfAKind from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "FourOfAKind") {
      assertThrows[InvalidPokerHandException](FourOfAKind(v))
    }
  }
 
}

