package shellPoker.gameEngine

class BettingManager(
    smallBlindValue: Int,
    bigBlindValue: Int,
    dealerButton: TableSeat,
    smallBlind: TableSeat,
    bigBlind: TableSeat,
    seats: List[TableSeat]) {
  
  private var roundEndingSeat: TableSeat = PositionHelper.getNextTakenSeat(bigBlind, seats)
  private var currentActionTaker: TableSeat = PositionHelper.getNextTakenSeat(bigBlind, seats)
  private var currentBettingRound: Int = 1
  private var lastBetSize: Int = bigBlindValue
  private var minBet: Int = bigBlindValue

  def isLegalAction(action: Action): Boolean = {
    action match{
      case Bet(amount) => canBet(amount)
      case Call => canCall
      case Check => canCheck()
      case Fold => true
      case AllIn => true
    }
  }

  def getNextActionTaker(): TableSeat = ???

  private def canCheck(): Boolean = {
    if(lastBetSize == 0) return true

    if(currentActionTaker == bigBlind && lastBetSize == bigBlindValue) return true

    return false
  }

  private def canBet(amount): Boolean = {
    currentActionTaker.player.chipStack.chipCount + 
      currentActionTaker.player.currentBetSize >= amount &&
      amount >= minBet

  }

  private def canCall(): Boolean = {
    currentActionTaker.player.chipStack.chipCount + 
      currentActionTaker.player.currentBetSize >= lastBetSize
  }


}