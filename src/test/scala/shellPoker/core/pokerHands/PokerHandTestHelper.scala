package shellPoker.core.pokerHands

import shellPoker.core.cards._

object PokerHandTestHelper {

  val pokerHands = Map(
      "HighCard" ->  List(
          Card(Queen, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "Pair" ->  List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Ace, Clubs),
          Card(King, Hearts),
          Card(Three, Spades)),
      "TwoPair" -> List(
          Card(King, Diamonds),
          Card(King, Spades),
          Card(Ace, Clubs),
          Card(Ace, Hearts),
          Card(Two, Spades)),
      "ThreeOfAKind" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(Ace, Hearts),
          Card(Three, Spades)),
      "Straight" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Nine, Clubs),
          Card(King, Hearts),
          Card(Ten, Spades)),
      "Flush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Three, Diamonds)),
      "FullHouse" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(King, Hearts),
          Card(King, Spades)),
      "FourOfAKind" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(Queen, Hearts),
          Card(Three, Spades)),
      "StraightFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Diamonds),
          Card(Eight, Diamonds),
          Card(Nine, Diamonds)),
      "RoyalFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Ten, Diamonds))
  )

  val betterPokerHands = Map(
      "HighCard" ->  List(
          Card(King, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "Pair" ->  List(
          Card(Queen, Diamonds),
          Card(Ace, Spades),
          Card(Ace, Clubs),
          Card(King, Hearts),
          Card(Three, Spades)),
      "TwoPair" -> List(
          Card(King, Diamonds),
          Card(King, Spades),
          Card(Ace, Clubs),
          Card(Ace, Hearts),
          Card(Three, Spades)),
      "ThreeOfAKind" -> List(
          Card(Queen, Diamonds),
          Card(Ace, Spades),
          Card(Ace, Clubs),
          Card(Ace, Hearts),
          Card(Three, Spades)),
      "Straight" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Ace, Clubs),
          Card(King, Hearts),
          Card(Ten, Spades)),
      "Flush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Four, Diamonds)),
      "FullHouse" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(Ace, Hearts),
          Card(Ace, Spades)),
      "FourOfAKind" -> List(
          Card(King, Diamonds),
          Card(King, Spades),
          Card(King, Clubs),
          Card(King, Hearts),
          Card(Three, Spades)),
      "StraightFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Diamonds),
          Card(King, Diamonds),
          Card(Nine, Diamonds)),
      "RoyalFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Ten, Diamonds))
  )

  val worsePokerHands = Map(
      "HighCard" ->  List(
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "Pair" ->  List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Three, Clubs),
          Card(King, Hearts),
          Card(Four, Spades)),
      "TwoPair" -> List(
          Card(King, Diamonds),
          Card(King, Spades),
          Card(Queen, Clubs),
          Card(Queen, Hearts),
          Card(Three, Spades)),
      "ThreeOfAKind" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(Ace, Hearts),
          Card(Two, Spades)),
      "Straight" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Eight, Clubs),
          Card(Ten, Hearts),
          Card(Nine, Spades)),
      "Flush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Four, Diamonds),
          Card(Three, Diamonds)),
      "FullHouse" -> List(
          Card(Jack, Diamonds),
          Card(Jack, Spades),
          Card(Jack, Clubs),
          Card(Ace, Hearts),
          Card(Ace, Spades)),
      "FourOfAKind" -> List(
          Card(Queen, Diamonds),
          Card(Queen, Spades),
          Card(Queen, Clubs),
          Card(Queen, Hearts),
          Card(Two, Spades)),
      "StraightFlush" -> List(
          Card(Seven, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Diamonds),
          Card(Eight, Diamonds),
          Card(Nine, Diamonds)),
      "RoyalFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Ten, Diamonds))
  )

  val confusingHands = Map(
      "HighCard" -> List(
          Card(Two, Diamonds),
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "HighCard2" -> List(
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Two, Diamonds),
          Card(Three, Hearts),
          Card(Four, Spades)),
      "Pair" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "Pair2" -> List(
          Card(Ace, Spades),
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Two, Clubs),
          Card(Two, Spades),
          Card(Three, Spades)),
      "TwoPair" -> List(
          Card(Ace, Spades),
          Card(Queen, Hearts),
          Card(Queen, Diamonds),
          Card(Jack, Hearts),
          Card(Two, Spades),
          Card(Two, Clubs),
          Card(Jack, Clubs)),
      "ThreeOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Ten, Spades),
          Card(Ace, Clubs),
          Card(Four, Hearts),
          Card(Three, Spades)),
      "ThreeOfAKind2" -> List(
          Card(Four, Spades),
          Card(Ace, Diamonds),
          Card(Five, Hearts),
          Card(Two, Spades),
          Card(Seven, Clubs),
          Card(Five, Clubs),
          Card(Five, Spades)),
      "Straight" -> List(
          Card(Queen, Spades),
          Card(Jack, Diamonds),
          Card(Ten, Hearts),
          Card(Queen, Clubs),
          Card(Nine, Hearts),
          Card(Eight, Hearts),
          Card(Seven, Spades)),
      "Straight2" -> List(
          Card(Ace, Spades),
          Card(King, Diamonds),
          Card(Two, Hearts),
          Card(Three, Clubs),
          Card(Four, Hearts),
          Card(Five, Hearts),
          Card(Queen, Spades)),
      "Flush" -> List(                
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Four, Diamonds),
          Card(Eight, Diamonds),
          Card(Queen, Hearts),
          Card(Queen, Spades)),
      "Flush2" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Four, Diamonds),
          Card(Three, Diamonds),
          Card(King, Diamonds),
          Card(Two, Diamonds)),
      "FullHouse" -> List(
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(King, Clubs),
          Card(King, Hearts),
          Card(Queen, Clubs),
          Card(Two, Diamonds)),
      "FourOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Queen, Clubs),
          Card(Ace, Clubs),
          Card(King, Hearts),
          Card(Three, Spades)),
      "StraightFlush" -> List(
          Card(Seven, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Diamonds),
          Card(Eight, Diamonds),
          Card(Nine, Diamonds),
          Card(Nine, Spades),
          Card(Nine, Hearts)),
      "RoyalFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Ten, Diamonds),
          Card(Nine, Diamonds),
          Card(Eight, Diamonds)))



  val correctHands = Map(
      "HighCard" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts)),
      "HighCard2" -> List(
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(Jack, Spades),
          Card(Four, Spades)),
      "Pair" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs)),
      "Pair2" -> List(
          Card(Ace, Spades),
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(Two, Clubs),
          Card(Two, Spades)),
      "TwoPair" -> List(
          Card(Ace, Spades),
          Card(Queen, Hearts),
          Card(Queen, Diamonds),
          Card(Jack, Hearts),
          Card(Jack, Spades)),
      "ThreeOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Ten, Spades),
          Card(Ace, Clubs)),
      "ThreeOfAKind2" -> List(
          Card(Ace, Diamonds),
          Card(Five, Hearts),
          Card(Seven, Clubs),
          Card(Five, Clubs),
          Card(Five, Spades)),
      "Straight" -> List( 
          Card(Queen, Clubs),
          Card(Jack, Diamonds),
          Card(Ten, Hearts),
          Card(Nine, Hearts),
          Card(Eight, Hearts)),
      "Straight2" -> List(
          Card(Ace, Spades),
          Card(Two, Hearts),
          Card(Three, Clubs),
          Card(Four, Hearts),
          Card(Five, Hearts)),
      "Flush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Eight, Diamonds),
          Card(Four, Diamonds)),
      "Flush2" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Four, Diamonds),
          Card(King, Diamonds)),
      "FullHouse" -> List(
          Card(King, Diamonds),
          Card(Queen, Diamonds),
          Card(King, Clubs),
          Card(King, Hearts),
          Card(Queen, Clubs)),
      "FourOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Queen, Clubs),
          Card(Ace, Clubs)),
      "StraightFlush" -> List(
          Card(Seven, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Diamonds),
          Card(Eight, Diamonds),
          Card(Nine, Diamonds)),
      "RoyalFlush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(King, Diamonds),
          Card(Ten, Diamonds))
  )



  val stringToPokerHand = Map(
    "HighCard" -> HighCard,
    "Pair" -> Pair,
    "ThreeOfAKind" -> ThreeOfAKind,
    "TwoPair" -> TwoPair,
    "Straight" -> Straight,
    "Flush" -> Flush,
    "FullHouse" -> FullHouse,
    "FourOfAKind" -> FourOfAKind,
    "StraightFlush" -> StraightFlush,
    "RoyalFlush" -> RoyalFlush)
}