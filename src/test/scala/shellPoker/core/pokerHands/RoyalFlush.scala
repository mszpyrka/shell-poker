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

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight")
    )

    for(i <- toCheck) assert(!RoyalFlush.isMadeUpOf(i))
  }

  test("Comparing different RoyalFlush hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "RoyalFlush" && k2 == "RoyalFlush" && k3 == "RoyalFlush"} {

          val hand = RoyalFlush(v1)
          val betterHand = RoyalFlush(v2)
          val worseHand = RoyalFlush(v3)
          val equalHand = RoyalFlush(v1)

          assert(!hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make RoyalFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "RoyalFlush") {
      assertThrows[InvalidPokerHandException](RoyalFlush(v))
    }
  }
 
}

