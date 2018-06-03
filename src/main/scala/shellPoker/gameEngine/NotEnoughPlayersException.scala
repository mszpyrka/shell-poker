package shellPoker.gameEngine

/** Thrown when there is an attempt to pick players' positions,
  * but the number of players at the table is less than two.
  */
case class NotEnoughPlayersException(message: String = "") extends Exception(message)
