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

  def setActive(): Unit = setStatus(IsActive)
  def setAllIn(): Unit = setStatus(IsAllIn)
  def setFolded(): Unit = setStatus(HasFolded)

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
  private def setStatus(status: Status): Unit = this.status = status

  /* Private helper class for keeping track of current player's status. */
  private sealed class Status
  private case object IsActive extends Status
  private case object HasFolded extends Status
  private case object IsAllIn extends Status

}