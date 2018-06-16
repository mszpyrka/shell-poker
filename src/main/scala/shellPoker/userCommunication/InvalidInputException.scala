package shellPoker.gameEngine

/** Thrown when there is an invalid input from user.
  */
case class InvalidInputException(message: String = "") extends Exception(message)
