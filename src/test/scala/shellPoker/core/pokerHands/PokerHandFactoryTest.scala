package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for PokerHandFactory class. */
class  PokerHandFactoryTest extends FunSuite {


  test("Every attempt to make a PokerHand from cards that do not make that hand should throw InvalidPokerHandException.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(Queen, Diamonds),
      Card(Ace, Clubs),
      Card(Two, Diamonds),
      Card(Two, Spades)
    )

    assertThrows[InvalidPokerHandException](HighCard(l1))


    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(Ace, Clubs),
      Card(Ten, Diamonds),
      Card(Jack, Spades),
      Card(Jack, Hearts)
    )

    assertThrows[InvalidPokerHandException](HighCard(l2))
  }

  test("PokerHandFactory should make the best possible hand out of 5 cards"){


    
    val highCards = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("HighCard")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("HighCard")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("HighCard")))


    for(i <- highCards) assert(i.isInstanceOf[HighCard])

    val pairs = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("Pair")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("Pair")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("Pair")))


    for(i <- pairs) assert(i.isInstanceOf[Pair])

    val twoPairs = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("TwoPair")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("TwoPair")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("TwoPair")))


    for(i <- twoPairs) assert(i.isInstanceOf[TwoPair])

    val threeOfAKinds = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("ThreeOfAKind")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("ThreeOfAKind")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("ThreeOfAKind")))


    for(i <- threeOfAKinds) assert(i.isInstanceOf[ThreeOfAKind])

    val straights = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("Straight")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("Straight")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("Straight")))


    for(i <- straights) assert(i.isInstanceOf[Straight])

    val flushs = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("Flush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("Flush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("Flush")))


    for(i <- flushs) assert(i.isInstanceOf[Flush])

    val fullHouses = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("FullHouse")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("FullHouse")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("FullHouse")))


    for(i <- fullHouses) assert(i.isInstanceOf[FullHouse])

    val fourOfAKinds = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("FourOfAKind")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("FourOfAKind")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("FourOfAKind")))


    for(i <- fourOfAKinds) assert(i.isInstanceOf[FourOfAKind])

    val straightFlushs = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("StraightFlush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("StraightFlush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("StraightFlush")))


    for(i <- straightFlushs) assert(i.isInstanceOf[StraightFlush])

    val royalFlushs = List(PokerHandFactory.pickBestHand(PokerHandTestHelper.pokerHands("RoyalFlush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.worsePokerHands("RoyalFlush")),
                         PokerHandFactory.pickBestHand(PokerHandTestHelper.betterPokerHands("RoyalFlush")))


    for(i <- royalFlushs) assert(i.isInstanceOf[RoyalFlush])

  }

  test("picking from 7 cards should result in the best hand possible"){
    for{
        (k1, v1) <- PokerHandTestHelper.confusingHands
        (k2, v2) <- PokerHandTestHelper.correctHands
        if k1 == k2} {
      val pickedHand = PokerHandFactory.pickBestHand(v1)
      val correctHand = PokerHandFactory.pickBestHand(v2)

      assert(pickedHand.isEquallyStrongAs(correctHand))
    }
  }
}

