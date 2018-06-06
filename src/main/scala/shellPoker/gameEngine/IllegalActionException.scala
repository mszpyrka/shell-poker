package shellPoker.gameEngine

/** Thrown when there is an attempt to add a player to taken or non-existing seat.
  */
case class IllegalActionException(illegal: Illegal) extends Exception(illegal.cause)
