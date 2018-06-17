package shellPoker.gameEngine.player

import shellPoker.core.cards.Card
import shellPoker.gameEngine.table.TableSeat


/** Represents a poker player.
  * Responsible for storing player's game state, hole cards, bet sizes and chip stack.
  */
class Player(val name: String, val seat: TableSeat, val chipStack: ChipStack) {

  private var _holeCards: (Card, Card) = _
  private var _currentBetSize: Int = 0
  private var gameStatus: GameStatus = IsActive
  private var cardsStatus: CardsStatus = None



  // ===================================================================================================================
  // Methods and fields related to player's gameStatus in currently played hand:
  // ===================================================================================================================

  /* Private helper class for keeping track of current player's gameStatus. */
  private sealed class GameStatus
  private case object IsActive extends GameStatus
  private case object HasFolded extends GameStatus
  private case object IsAllIn extends GameStatus

  /* Sets player's game gameStatus. */
  private def setStatus(status: GameStatus): Unit = this.gameStatus = status

  def isActive: Boolean = this.gameStatus == IsActive
  def hasFolded: Boolean = this.gameStatus == HasFolded
  def isAllIn: Boolean = this.gameStatus == IsAllIn

  def setActive(): Unit = setStatus(IsActive)
  def setAllIn(): Unit = setStatus(IsAllIn)
  def setFolded(): Unit = setStatus(HasFolded)



  // ===================================================================================================================
  // Methods and fields related to player's cards:
  // ===================================================================================================================

  private sealed class CardsStatus
  private case object None extends CardsStatus
  private case object Hidden extends CardsStatus
  private case object Showed extends CardsStatus
  private case object Mucked extends CardsStatus

  def holeCards: (Card, Card) = _holeCards

  def setHoleCards(c1: Card, c2: Card): Unit = {

    this.cardsStatus = Hidden
    _holeCards = (c1, c2)
  }
  def resetHoleCards(): Unit = {

    this.cardsStatus = None
    _holeCards = null
  }

  def showCards(): Unit = this.cardsStatus = Showed
  def muckCards(): Unit = this.cardsStatus = Mucked

  def showedCards: Boolean = this.cardsStatus == Showed
  def muckedCards: Boolean = this.cardsStatus == Mucked



  // ===================================================================================================================
  // Methods and fields related to player's chip stack and bet size:
  // ===================================================================================================================

  /* Returns the chip count in current player's bet. */
  def currentBetSize: Int = _currentBetSize

  /* Removes proper chips amount from player's stack to make their bet match given size. */
  def setBetSize(amount: Int): Unit = {

    chipStack.removeChips(amount - _currentBetSize)
    _currentBetSize = amount

    if (chipStack.chipCount == 0)
      setStatus(IsAllIn)
  }

  /** Makes player put given amount of chips into their current bet.
    * Goes all-in if there is not enough chips in player's stack.
    */
  def postBlind(amount: Int): Unit = {

    if (amount > chipStack.chipCount)
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
}