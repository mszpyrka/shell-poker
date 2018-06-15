package shellPoker.gameEngine

/** Thrown when there is an invalid input from user.
  */
case class InvlidInputeException(message: String = "") extends Exception(message)
