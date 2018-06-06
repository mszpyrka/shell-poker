package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for StraightFlush class. */
class StraightFlushTest extends FunSuite {

  test("Certain cards should make at least a StraightFlush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("StraightFlush"),
                       PokerHandTestHelper.betterPokerHands("StraightFlush"),
                       PokerHandTestHelper.worsePokerHands("StraightFlush"),
                       PokerHandTestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(StraightFlush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a StraightFlush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("FourOfAKind"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.worsePokerHands("HighCard"),
    )

    for(i <- toCheck) assert(!StraightFlush.isMadeUpOf(i))
  }

  test("Comparing different StraightFlush hands.") {

    val hand = StraightFlush(PokerHandTestHelper.pokerHands("StraightFlush"))
    val worseHand = StraightFlush(PokerHandTestHelper.worsePokerHands("StraightFlush"))
    val betterHand =  StraightFlush(PokerHandTestHelper.betterPokerHands("StraightFlush"))
    val equalHand = StraightFlush(PokerHandTestHelper.pokerHands("StraightFlush"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make StraightFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "StraightFlush") {
      assertThrows[InvalidPokerHandException](StraightFlush(v))
    }
  }
 
}

