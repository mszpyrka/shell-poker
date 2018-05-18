package gameEngine.cards

case class Card(rank: Rank, suit: Suit) {
  def compare(other: Card): Int = rank.compare(other.rank)
}