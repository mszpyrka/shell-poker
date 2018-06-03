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

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "FourOfAKind" && k2 == "FourOfAKind" && k3 == "FourOfAKind"} {

          val hand = FourOfAKind(v1)
          val betterHand = FourOfAKind(v2)
          val worseHand = FourOfAKind(v3)
          val equalHand = FourOfAKind(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make FourOfAKind from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "FourOfAKind") {
      assertThrows[InvalidPokerHandException](FourOfAKind(v))
    }
  }
 
}

