package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for StraightFlush class. */
class StraightFlushTest extends FunSuite {

  test("Certain cards should make at least a StraightFlush hand") {

    val toCheck = List(TestHelper.pokerHands("StraightFlush"),
                       TestHelper.betterPokerHands("StraightFlush"),
                       TestHelper.worsePokerHands("StraightFlush"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(StraightFlush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a StraightFlush hand") {

    val toCheck = List(TestHelper.pokerHands("FourOfAKind"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.worsePokerHands("HighCard"),
    )

    for(i <- toCheck) assert(!StraightFlush.isMadeUpOf(i))
  }

  test("Comparing different StraightFlush hands.") {

    val hand = StraightFlush(TestHelper.pokerHands("StraightFlush"))
    val worseHand = StraightFlush(TestHelper.worsePokerHands("StraightFlush"))
    val betterHand =  StraightFlush(TestHelper.betterPokerHands("StraightFlush"))
    val equalHand = StraightFlush(TestHelper.pokerHands("StraightFlush"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make StraightFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "StraightFlush") {
      assertThrows[InvalidPokerHandException](StraightFlush(v))
    }
  }
 
}

