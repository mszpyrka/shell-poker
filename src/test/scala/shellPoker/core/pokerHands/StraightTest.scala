package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Straight class. */
class StraightTest extends FunSuite {

  test("Certain cards should make at least a Straight hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Straight"),
                       PokerHandTestHelper.betterPokerHands("Straight"),
                       PokerHandTestHelper.worsePokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("StraightFlush")
    )

    for(i <- toCheck) assert(Straight.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Straight hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.pokerHands("Flush")
    )

    for(i <- toCheck) assert(!Straight.isMadeUpOf(i))
  }

  test("Comparing different Straight hands.") {

    val hand = Straight(PokerHandTestHelper.pokerHands("Straight"))
    val worseHand = Straight(PokerHandTestHelper.worsePokerHands("Straight"))
    val betterHand =  Straight(PokerHandTestHelper.betterPokerHands("Straight"))
    val equalHand = Straight(PokerHandTestHelper.pokerHands("Straight"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make Straight from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "Straight") {
      assertThrows[InvalidPokerHandException](Straight(v))
    }
  }
 
}

