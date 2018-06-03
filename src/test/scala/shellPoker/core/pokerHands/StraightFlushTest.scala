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

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
    )

    for(i <- toCheck) assert(!StraightFlush.isMadeUpOf(i))
  }

  test("Comparing different StraightFlush hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "StraightFlush" && k2 == "StraightFlush" && k3 == "StraightFlush"} {

          val hand = StraightFlush(v1)
          val betterHand = StraightFlush(v2)
          val worseHand = StraightFlush(v3)
          val equalHand = StraightFlush(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make StraightFlush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "StraightFlush") {
      assertThrows[InvalidPokerHandException](StraightFlush(v))
    }
  }
 
}

