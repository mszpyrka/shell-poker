package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for HighCard class. */
class HighCardTest extends FunSuite {

  test("Every 5 cards make at least HighCard hand.") {

    val toCheck = List(TestHelper.pokerHands("HighCard"),
                       TestHelper.betterPokerHands("HighCard"),
                       TestHelper.worsePokerHands("HighCard"),
                       TestHelper.pokerHands("Straight")
    )

    for(i <- toCheck) assert(HighCard.isMadeUpOf(i))

  }

  test("Comparing different HighCard hands.") {

    val hand = HighCard(TestHelper.pokerHands("HighCard"))
    val worseHand = HighCard(TestHelper.worsePokerHands("HighCard"))
    val betterHand =  HighCard(TestHelper.betterPokerHands("HighCard"))
    val equalHand = HighCard(TestHelper.pokerHands("HighCard"))

    assert(hand.isStrongerThan(worseHand))
    assert(hand.isWeakerThan(betterHand))
    assert(hand.isEquallyStrongAs(equalHand))
    assert(worseHand.isWeakerThan(betterHand))

  }


  test("Every attempt to make HighCard from cards that make better hand should throw InvalidPokerHandException.") {

    for ((k, v) <- TestHelper.pokerHands if k != "HighCard") {
      assertThrows[InvalidPokerHandException](HighCard(v))

    }

  }
 
}

