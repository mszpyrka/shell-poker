package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for RoyalFlush class. */
class RoyalFlushTest extends FunSuite {

  test("Certain cards should make at least a RoyalFlush hand") {

    val toCheck = List(TestHelper.pokerHands("RoyalFlush"),
                       TestHelper.betterPokerHands("RoyalFlush"),
                       TestHelper.worsePokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(RoyalFlush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a RoyalFlush hand") {

    val toCheck = List(TestHelper.pokerHands("ThreeOfAKind"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight")
    )

    for(i <- toCheck) assert(!RoyalFlush.isMadeUpOf(i))
  }

  test("Comparing different RoyalFlush hands.") {

    val hand = RoyalFlush(TestHelper.pokerHands("RoyalFlush"))
    val worseHand = RoyalFlush(TestHelper.worsePokerHands("RoyalFlush"))
    val betterHand =  RoyalFlush(TestHelper.betterPokerHands("RoyalFlush"))
    val equalHand = RoyalFlush(TestHelper.pokerHands("RoyalFlush"))

    assert(!hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make RoyalFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "RoyalFlush") {
      assertThrows[InvalidPokerHandException](RoyalFlush(v))
    }
  }
 
}

