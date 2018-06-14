package shellPoker.gameEngine

import shellPoker.core.cards.Card


object Player{
  
}

/*Represents a poker player
  *Responsible for storing player's game state
  */
class Player(val chipStack: ChipStack) {

  private var _holeCards: (Card, Card) = _
  private var _currentBetSize: Int = 0
  private var status: Status = IsActive

  def isActive: Boolean = this.status == IsActive
  def hasFolded: Boolean = this.status == HasFolded
  def isAllIn: Boolean = this.status == IsAllIn

  def setActive(): Unit = setStatus(IsActive)
  def setAllIn(): Unit = setStatus(IsAllIn)
  def setFolded(): Unit = setStatus(HasFolded)

  def holeCards: (Card, Card) = _holeCards
  def setHoleCards(c1: Card, c2: Card): Unit = _holeCards = (c1, c2)
  def resetHoleCards(): Unit = _holeCards = null

  /* Returns the chip count in current player's bet. */
  def currentBetSize: Int = _currentBetSize

  /* Removes proper chips amount from player's stack to make their bet match given size. */
  def setBetSize(amount: Int): Unit = {

    chipStack.removeChips(amount - _currentBetSize)
    _currentBetSize = amount

    if (chipStack.chipCount == 0)
      setStatus(IsAllIn)
  }

  /** Makes player put some amount of chips into their current bet.
    * Goes all-in if there is not enough chips in player's stack).
    */
  def postBlind(amount: Int): Unit = {

    if (amount > currentBetSize)
      goAllIn()

    else
      setBetSize(amount)
  }

  /* Decreases player's current bet size by given amount. */
  def removeChipsFromBet(amount: Int): Unit = {

    if (amount > currentBetSize)
      throw NegativeChipCountException()

    _currentBetSize -= amount
  }

  /* Makes all-in bet. */
  def goAllIn(): Unit = {

    _currentBetSize += chipStack.chipCount
    chipStack.removeChips(chipStack.chipCount)
    setAllIn()
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