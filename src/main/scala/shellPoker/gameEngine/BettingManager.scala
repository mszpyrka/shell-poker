package shellPoker.gameEngine

/** Responsible for
  *
  * @param smallBlindValue
  * @param bigBlindValue
  * @param dealerButton
  * @param smallBlind
  * @param bigBlind
  * @param seats
  */
class BettingManager(
    smallBlindValue: Int,
    bigBlindValue: Int,
    dealerButton: TableSeat,
    smallBlind: TableSeat,
    bigBlind: TableSeat,
    seats: List[TableSeat]) {

  private var currentBettingRound: Int = 0
  private var roundEndingSeat: TableSeat = _
  private var currentActionTaker: TableSeat = _
  private var lastBetSize: Int = _
  private var minBet: Int = _

  def startNextRound(): Unit = {

    currentBettingRound += 1
    minBet = bigBlindValue

    if (currentBettingRound == 1) {

      roundEndingSeat = PositionHelper.getNextActiveSeat(bigBlind, seats)
      currentActionTaker = PositionHelper.getNextActiveSeat(bigBlind, seats)
      lastBetSize = bigBlindValue
    }

    else {

      roundEndingSeat = PositionHelper.getNextActiveSeat(dealerButton, seats)
      currentActionTaker = PositionHelper.getNextActiveSeat(dealerButton, seats)
      lastBetSize = 0
    }
  }

  def validateAction(action: Action): ActionValidation = {
    action match{
      case Bet(amount) => canBet(amount)
      case Call => canCall
      case Check => canCheck
      case Fold => canFold
      case AllIn => canGoAllIn
    }
  }

  def getActionTaker: TableSeat = currentActionTaker

  def proceedWithAction(action: Action): Unit = ???

  private def nextActionTaker: TableSeat = {

    val nextActiveSeat = PositionHelper.getNextActiveSeat(currentActionTaker, seats)
    if (nextActiveSeat == roundEndingSeat)
      return null

    nextActiveSeat
  }

  private def canCheck: ActionValidation = {

    if((lastBetSize == 0) ||
        (currentActionTaker == bigBlind && lastBetSize == bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }

  private def canBet(amount: Int): ActionValidation = {

    if (currentActionTaker.player.chipStack.chipCount +
        currentActionTaker.player.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount >= minBet)
      return Illegal(s"Bet size cannot be smaller than $minBet.")

    Legal
  }

  private def canCall: ActionValidation = {

    if(currentActionTaker.player.chipStack.chipCount +
        currentActionTaker.player.currentBetSize < lastBetSize)
      return Illegal("Not enough chips to call.")

    Legal

  }

  private def canFold: ActionValidation = Legal

  private def canGoAllIn: ActionValidation = Legal
}