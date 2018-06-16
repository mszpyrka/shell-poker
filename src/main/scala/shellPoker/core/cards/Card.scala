package shellPoker.core.cards

/** Represents playing card entity.
  *
  * @param rank card's rank
  * @param suit card's suit
  */
case class Card(rank: CardRank, suit: Suit) {
  override def toString: String = {
    var result: String = ""

    this match {
      case Card(Ace, _) => result += "A"
      case Card(King, _) => result += "K"
      case Card(Queen, _) => result += "Q"
      case Card(Jack, _) => result += "J"
      case Card(Ten, _) => result += "T"
      case Card(Nine, _) => result += "9"
      case Card(Eight, _) => result += "8"
      case Card(Seven, _) => result += "7"
      case Card(Six, _) => result += "6"
      case Card(Five, _) => result += "5"
      case Card(Four, _) => result += "4"
      case Card(Three, _) => result += "3"
      case Card(Two, _) => result += "2"
      case _ => result += ""
    }

    this match {
      case Card(_, Spades) => result += s"${Console.WHITE}\u2660"
      case Card(_, Hearts) => result += s"${Console.RED}\u2665${Console.RESET}"
      case Card(_, Diamonds) => result += s"${Console.RED}\u2666${Console.RESET}"
      case Card(_, Clubs) => result += s"${Console.WHITE}\u2663"
      case _ => result += ""
    }

    result
  }
}