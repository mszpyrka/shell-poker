package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Flush class. */
class FlushTest extends FunSuite {

  test("Certain cards should make at least a Flush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Flush"),
                       PokerHandTestHelper.betterPokerHands("Flush"),
                       PokerHandTestHelper.worsePokerHands("Flush"),
                       PokerHandTestHelper.pokerHands("StraightFlush")
    )

    for(i <- toCheck) assert(Flush.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Flush hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(!Flush.isMadeUpOf(i))
  }

  test("Comparing different Flush hands.") {

    val hand = Flush(PokerHandTestHelper.pokerHands("Flush"))
    val worseHand = Flush(PokerHandTestHelper.worsePokerHands("Flush"))
    val betterHand =  Flush(PokerHandTestHelper.betterPokerHands("Flush"))
    val equalHand = Flush(PokerHandTestHelper.pokerHands("Flush"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make Flush from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "Flush") {
      assertThrows[InvalidPokerHandException](Flush(v))
    }
  }
 
}

