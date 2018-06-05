package shellPoker.gameEngine

import shellPoker.core.cards.Card


object Player{
  
}

/*Represents a poker player
  *Responsible for storing player's game state
  */
class Player(val chipStack: ChipStack) {

  var holeCards: (Card, Card) = _
  private var _currentBetSize: Int = 0
  private var status: Status = IsActive

  def isActive: Boolean = this.status == IsActive
  def hasFolded: Boolean = this.status == HasFolded
  def isAllIn: Boolean = this.status == IsAllIn

  /* Returns the chip count in current player's bet. */
  def currentBetSize: Int = _currentBetSize

  /* Removes given chips amount from player's stack and adds it to player's bet. */
  def makeBet(amount: Int): Unit = {

    chipStack.removeChips(amount)
    _currentBetSize += amount

    if (chipStack.chipCount == 0)
      setStatus(IsAllIn)
  }

  /* Resets players current bet */
  def resetCurrentBet(): Unit = _currentBetSize = 0

  /* Sets player's game status. */
  def setStatus(status: Status): Unit = this.status = status

  /* Private helper class for keeping track of current player's status. */
  sealed class Status
  case object IsActive extends Status
  case object HasFolded extends Status
  case object IsAllIn extends Status

}