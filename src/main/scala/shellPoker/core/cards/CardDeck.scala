package shellPoker.core.cards

/* Represents a deck of cards. */
object CardDeck {

  val deck: List[Card] = for {
    rank <- CardRank.ranks
    suit <- Suit.suits
  } yield Card(rank, suit)

}
