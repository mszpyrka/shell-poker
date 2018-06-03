package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for HighCard class. */
class HighCardTest extends FunSuite {

  test("Every 5 cards make at least HighCard hand.") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("HighCard"),
                       TestHelper.worsePokerHands("HighCard"),
                       TestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(HighCard.isMadeUpOf(i))
  }

  test("Comparing different HighCard hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "HighCard" && k2 == "HighCard" && k3 == "HighCard"} {

          val hand = HighCard(v1)
          val betterHand = HighCard(v2)
          val worseHand = HighCard(v3)
          val equalHand = HighCard(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make HighCard from cards that make better hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "HighCard") {
      assertThrows[InvalidPokerHandException](HighCard(v))
    }
  }
 
}

