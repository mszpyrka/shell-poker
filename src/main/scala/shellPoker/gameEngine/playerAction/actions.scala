package shellPoker.gameEngine.playerAction

/* Represents any action that may be taken during the game. */
sealed trait Action

case object Fold extends Action
case object Check extends Action
case object Call extends Action
case class Bet(amount: Int) extends Action
case class Raise(amount: Int) extends Action
case object AllIn extends Action


/* Represents action's validation status. */
sealed trait ActionValidation

case object Legal extends ActionValidation
case class Illegal(cause: String) extends ActionValidation
