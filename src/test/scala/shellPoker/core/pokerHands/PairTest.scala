package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Pair class. */
class PairTest extends FunSuite {

  test("Certain cards should make at least a Pair hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Pair"),
                       TestHelper.worsePokerHands("Pair"),
                       TestHelper.pokerHands("ThreeOfAKind"),
                       TestHelper.pokerHands("FourOfAKind"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(Pair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Pair hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!Pair.isMadeUpOf(i))
  }

  test("Comparing different Pair hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "Pair" && k2 == "Pair" && k3 == "Pair"} {

          val hand = Pair(v1)
          val betterHand = Pair(v2)
          val worseHand = Pair(v3)
          val equalHand = Pair(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make Pair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "Pair") {
      assertThrows[InvalidPokerHandException](Pair(v))
    }
  }
 
}

