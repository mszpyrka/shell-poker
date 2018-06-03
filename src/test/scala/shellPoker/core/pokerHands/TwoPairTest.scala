package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for TwoPair class. */
class TwoPairTest extends FunSuite {

  test("Certain cards should make at least a TwoPair hand") {

    val toCheck = List(TestHelper.pokerHands("TwoPair"),
                       TestHelper.betterPokerHands("TwoPair"),
                       TestHelper.worsePokerHands("TwoPair"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(TwoPair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a TwoPair hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!TwoPair.isMadeUpOf(i))
  }

  test("Comparing different TwoPair hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "TwoPair" && k2 == "TwoPair" && k3 == "TwoPair"} {

          val hand = TwoPair(v1)
          val betterHand = TwoPair(v2)
          val worseHand = TwoPair(v3)
          val equalHand = TwoPair(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make TwoPair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "TwoPair") {
      assertThrows[InvalidPokerHandException](TwoPair(v))
    }
  }
 
}

