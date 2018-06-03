package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Flush class. */
class FlushTest extends FunSuite {

  test("Certain cards should make at least a Flush hand") {

    val toCheck = List(TestHelper.pokerHands("Flush"),
                       TestHelper.betterPokerHands("Flush"),
                       TestHelper.worsePokerHands("Flush"),
                       TestHelper.pokerHands("StraightFlush")
    )

    for(i <- toCheck) assert(Flush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Flush hand") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.worsePokerHands("Straight"),
                       TestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(!Flush.isMadeUpOf(i))
  }

  test("Comparing different Flush hands.") {

    for {(k1, v1) <- TestHelper.pokerHands
         (k2, v2) <- TestHelper.betterPokerHands 
         (k3, v3) <- TestHelper.worsePokerHands 
         if k1 == "Flush" && k2 == "Flush" && k3 == "Flush"} {

          val hand = Flush(v1)
          val betterHand = Flush(v2)
          val worseHand = Flush(v3)
          val equalHand = Flush(v1)

          assert(hand.isStrongerThan(worseHand))
          assert(hand.isWeakerThan(betterHand))
          assert(hand.isEquallyStrongAs(equalHand))
          assert(worseHand.isWeakerThan(betterHand))
    }
  }


  test("Every attempt to make Flush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- TestHelper.pokerHands if k != "Flush") {
      assertThrows[InvalidPokerHandException](Flush(v))
    }
  }
 
}

