package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for FullHouse class. */
class FullHouseTest extends FunSuite {

  test("Certain cards should make at least a FullHouse hand") {

    val toCheck = List(TestHelper.pokerHands("FullHouse"),
                       TestHelper.betterPokerHands("FullHouse"),
                       TestHelper.worsePokerHands("FullHouse"),
    )

    for(i <- toCheck) assert(FullHouse.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a FullHouse hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!FullHouse.isMadeUpOf(i))
  }

  test("Comparing different FullHouse hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "FullHouse" && k2 == "FullHouse" && k3 == "FullHouse"} {

          val hand = FullHouse(v1)
          val betterHand = FullHouse(v2)
          val worseHand = FullHouse(v3)
          val equalHand = FullHouse(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make FullHouse from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "FullHouse") {
      assertThrows[InvalidPokerHandException](FullHouse(v))
    }
  }
 
}

