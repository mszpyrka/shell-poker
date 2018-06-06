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


    
    val highCards = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("HighCard")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("HighCard")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("HighCard")))


    for(i <- highCards) assert(i.isInstanceOf[HighCard])

    val pairs = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("Pair")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("Pair")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("Pair")))


    for(i <- pairs) assert(i.isInstanceOf[Pair])

    val twoPairs = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("TwoPair")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("TwoPair")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("TwoPair")))


    for(i <- twoPairs) assert(i.isInstanceOf[TwoPair])

    val threeOfAKinds = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("ThreeOfAKind")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("ThreeOfAKind")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("ThreeOfAKind")))


    for(i <- threeOfAKinds) assert(i.isInstanceOf[ThreeOfAKind])

    val straights = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("Straight")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("Straight")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("Straight")))


    for(i <- straights) assert(i.isInstanceOf[Straight])

    val flushs = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("Flush")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("Flush")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("Flush")))


    for(i <- flushs) assert(i.isInstanceOf[Flush])

    val fullHouses = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("FullHouse")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("FullHouse")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("FullHouse")))


    for(i <- fullHouses) assert(i.isInstanceOf[FullHouse])

    val fourOfAKinds = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("FourOfAKind")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("FourOfAKind")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("FourOfAKind")))


    for(i <- fourOfAKinds) assert(i.isInstanceOf[FourOfAKind])

    val straightFlushs = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("StraightFlush")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("StraightFlush")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("StraightFlush")))


    for(i <- straightFlushs) assert(i.isInstanceOf[StraightFlush])

    val royalFlushs = List(PokerHandFactory.pickBestHand(TestHelper.pokerHands("RoyalFlush")),
                         PokerHandFactory.pickBestHand(TestHelper.worsePokerHands("RoyalFlush")),
                         PokerHandFactory.pickBestHand(TestHelper.betterPokerHands("RoyalFlush")))


    for(i <- royalFlushs) assert(i.isInstanceOf[RoyalFlush])

  }

  test("picking from 7 cards should result in the best hand possible"){
    for{
        (k1, v1) <- TestHelper.confusingHands
        (k2, v2) <- TestHelper.correctHands
        if k1 == k2} {
      val pickedHand = PokerHandFactory.pickBestHand(v1)
      val correctHand = PokerHandFactory.pickBestHand(v2)

      assert(pickedHand.isEquallyStrongAs(correctHand))
    }
  }
}

