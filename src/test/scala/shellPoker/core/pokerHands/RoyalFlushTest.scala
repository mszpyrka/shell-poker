package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for RoyalFlush class. */
class RoyalFlushTest extends FunSuite {

  test("Certain cards should make at least a RoyalFlush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("RoyalFlush"),
                       PokerHandTestHelper.betterPokerHands("RoyalFlush"),
                       PokerHandTestHelper.worsePokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(RoyalFlush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a RoyalFlush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("ThreeOfAKind"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Straight")
    )

    for(i <- toCheck) assert(!RoyalFlush.isMadeUpOf(i))
  }

  test("Comparing different RoyalFlush hands.") {

    val hand = RoyalFlush(PokerHandTestHelper.pokerHands("RoyalFlush"))
    val worseHand = RoyalFlush(PokerHandTestHelper.worsePokerHands("RoyalFlush"))
    val betterHand =  RoyalFlush(PokerHandTestHelper.betterPokerHands("RoyalFlush"))
    val equalHand = RoyalFlush(PokerHandTestHelper.pokerHands("RoyalFlush"))

    assert(!hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make RoyalFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "RoyalFlush") {
      assertThrows[InvalidPokerHandException](RoyalFlush(v))
    }
  }
 
}

