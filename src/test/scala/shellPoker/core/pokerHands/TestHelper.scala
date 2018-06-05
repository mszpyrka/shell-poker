package shellPoker.core.pokerHands

import shellPoker.core.cards._

object TestHelper{

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
      "Pair" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts),
          Card(Three, Spades)),
      "ThreeOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Ten, Spades),
          Card(Ace, Clubs),
          Card(Four, Hearts),
          Card(Three, Spades)),
      "Straight" -> List(
          Card(Queen, Spades),
          Card(Jack, Diamonds),
          Card(Ten, Hearts),
          Card(Queen, Clubs),
          Card(Nine, Hearts),
          Card(Eight, Hearts),
          Card(Seven, Spades)),
      "Flush" -> List(                
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Four, Diamonds),
          Card(Eight, Diamonds),
          Card(Queen, Hearts),
          Card(Queen, Spades)),
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
          Card(Eight, Diamonds))
  )

  val correctHands = Map(
      "HighCard" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs),
          Card(Five, Hearts)),
      "Pair" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ten, Spades),
          Card(Seven, Clubs)),
      "ThreeOfAKind" -> List(
          Card(Queen, Spades),
          Card(Queen, Diamonds),
          Card(Queen, Hearts),
          Card(Ten, Spades),
          Card(Ace, Clubs)),
      "Straight" -> List( 
          Card(Queen, Clubs),
          Card(Jack, Diamonds),
          Card(Ten, Hearts),
          Card(Nine, Hearts),
          Card(Eight, Hearts)),
      "Flush" -> List(
          Card(Queen, Diamonds),
          Card(Jack, Diamonds),
          Card(Ace, Diamonds),
          Card(Eight, Diamonds),
          Card(Four, Diamonds)),
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