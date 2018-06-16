package shellPoker.gameEngine.table

/** Thrown when there is an attempt to add a player to taken or non-existing seat.
  */
case class InvalidSeatException(message: String = "") extends Exception(message)
