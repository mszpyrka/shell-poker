package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Pair class. */
class PairTest extends FunSuite {

  test("Certain cards should make at least a Pair hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("Pair"),
                       PokerHandTestHelper.betterPokerHands("Pair"),
                       PokerHandTestHelper.worsePokerHands("Pair"),
                       PokerHandTestHelper.pokerHands("ThreeOfAKind"),
                       PokerHandTestHelper.pokerHands("FourOfAKind"),
                       PokerHandTestHelper.pokerHands("FullHouse")
    )

    for(i <- toCheck) assert(Pair.isMadeUpOf(i))
  }

  test("Certain cards should NOT make a Pair hand") {

    val toCheck = List(PokerHandTestHelper.pokerHands("HighCard"),
                       PokerHandTestHelper.pokerHands("Flush"),
                       PokerHandTestHelper.pokerHands("Straight"),
                       PokerHandTestHelper.pokerHands("RoyalFlush")
    )

    for(i <- toCheck) assert(!Pair.isMadeUpOf(i))
  }

  test("Comparing different Pair hands.") {

    val hand = Pair(PokerHandTestHelper.pokerHands("Pair"))
    val worseHand = Pair(PokerHandTestHelper.worsePokerHands("Pair"))
    val betterHand =  Pair(PokerHandTestHelper.betterPokerHands("Pair"))
    val equalHand = Pair(PokerHandTestHelper.pokerHands("Pair"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))
  }


  test("Every attempt to make Pair from cards that make better or worse hand should throw InvalidPokerHandException.") {
    for ((k, v) <- PokerHandTestHelper.pokerHands if k != "Pair") {
      assertThrows[InvalidPokerHandException](Pair(v))
    }
  }
 
}

