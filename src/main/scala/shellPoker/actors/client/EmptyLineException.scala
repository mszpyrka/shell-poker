package shellPoker.actors.client

/** Thrown when there is an invalid input from user.
  */
case object EmptyLineException extends Exception("no input was found")
