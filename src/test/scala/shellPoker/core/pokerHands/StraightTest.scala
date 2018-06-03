package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Straight class. */
class StraightTest extends FunSuite {

  test("Certain cards should make at least a Straight hand") {

    val toCheck = List(TestHelper.pokerHands("Straight"),
                       TestHelper.betterPokerHands("Straight"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("StraightFlush")
    )

    for(i <- toCheck) assert(Straight.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Straight hand") {

    val toCheck = List(TestHelper.pokerHands("Pair"),
                       TestHelper.betterPokerHands("Flush")
    )

    for(i <- toCheck) assert(!Straight.isMadeUpOf(i))
  }

  test("Comparing different Straight hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "Straight" && k2 == "Straight" && k3 == "Straight"} {

          val hand = Straight(v1)
          val betterHand = Straight(v2)
          val worseHand = Straight(v3)
          val equalHand = Straight(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make Straight from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "Straight") {
      assertThrows[InvalidPokerHandException](Straight(v))
    }
  }
 
}

