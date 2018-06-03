package shellPoker.gameEngine

/* Represents any action that may be taken during the game. */
sealed trait Action

case object Fold extends Action
case object Check extends Action
case object Call extends Action
case class Bet(betSize: Int) extends Action
case object AllIn extends Action