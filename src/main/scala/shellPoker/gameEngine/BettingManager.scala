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

    if (currentBettingRound == 1) {

      roundEndingSeat = PositionHelper.getNextTakenSeat(bigBlind, seats)
      currentActionTaker = PositionHelper.getNextTakenSeat(bigBlind, seats)
      lastBetSize = bigBlindValue
      minBet = bigBlindValue
    }

    else if (???)
    ???
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

  def getNextActionTaker(): TableSeat = {
    ???
    val activeSeats: List[TableSeat] = this.seats.filter(!_.isEmpty && _.player)
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