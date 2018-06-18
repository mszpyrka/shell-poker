package shellPoker.actors.actorUtils.client

import shellPoker.actors.client.LoggerActor
import shellPoker.core.cards.Card
import shellPoker.gameEngine.gameplay.status.{EndingStatus, GameStatus, HandStatus}

class LoggerFormatter {

  def formatSuccessfulLogin: String = ???
  def formatUnsuccessfulLogin: String = ???
  def formatHoleCards(c1: Card, c2: Card): String = ???
  def formatHandStatus(handStatus: HandStatus): String = ???
  def formatGameStatus(gameStatus: GameStatus): String = ???
  def formatEndingStatus(endingStatus: EndingStatus): String = ???
  def formatOther(other: Object): String = other.toString
  def formatActionRequest: String => ???
}
