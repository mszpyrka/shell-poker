package shellPoker.gameEngine

import shellPoker.core.pokerHands.RoyalFlush

/** Responsible for being a part of the program.
  *
  * @param bigBlindValue
  * @param dealerButton
  * @param bigBlind
  * @param table
  */
class BettingManager(
    bigBlindValue: Int,
    dealerButton: TableSeat,
    bigBlind: TableSeat,
    table: PokerTable) {

  private var currentBettingRound: Int = 0
  private var roundEndingSeat: TableSeat = _
  private var _actionTaker: TableSeat = _
  private var lastBetSize: Int = _
  private var minBet: Int = _
  private var minRaise: Int = _


  def startNextRound(): Unit = {

    currentBettingRound += 1
    minBet = bigBlindValue
    minRaise = bigBlindValue

    if (currentBettingRound == 1) {

      roundEndingSeat = table.getNextActiveSeat(bigBlind)
      _actionTaker = table.getNextActiveSeat(bigBlind)
      lastBetSize = bigBlindValue
    }

    else {

      roundEndingSeat = table.getNextActiveSeat(dealerButton)
      _actionTaker = table.getNextActiveSeat(dealerButton)
      lastBetSize = 0
    }
  }

  def validateAction(action: Action): ActionValidation = {
    action match{
      case Bet(amount) => canBet(amount)
      case Raise(amount) => canRaise(amount)
      case Call => canCall
      case Check => canCheck
      case Fold => canFold
      case AllIn(amount) => canGoAllIn(amount)
    }
  }

  def actionTaker: TableSeat = _actionTaker

  def proceedWithAction(action: Action): Unit = {

    action match {

      case Bet(amount) => {

        minRaise = amount - lastBetSize
        minBet = amount + minRaise
        lastBetSize = amount
        roundEndingSeat = _actionTaker
      }

      case Raise(amount) => {

        minRaise = amount
        lastBetSize = lastBetSize + amount
        minBet = lastBetSize + amount
        roundEndingSeat = _actionTaker
      }

      case AllIn(amount) => {

        if (amount > lastBetSize) {

          val raised: Int = amount - lastBetSize
          lastBetSize = amount

          if (raised > minRaise)
            minRaise = raised

          minBet = lastBetSize + minRaise

          roundEndingSeat = _actionTaker
        }
      }

      case Call => ()
      case Fold => ()
      case Check => ()
    }

   _actionTaker = nextActionTaker
  }

  private def nextActionTaker: TableSeat = {

    val nextActiveSeat = table.getNextActiveSeat(_actionTaker)
    if (nextActiveSeat == roundEndingSeat)
      return null

    nextActiveSeat
  }

  private def canCheck: ActionValidation = {

    if((lastBetSize == 0) ||
        (_actionTaker == bigBlind && lastBetSize == bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }

  private def canBet(amount: Int): ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount >= minBet)
      return Illegal(s"Bet size cannot be smaller than $minBet.")

    Legal
  }

  private def canRaise(amount: Int): ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < lastBetSize + amount)
      return Illegal("Not enough chips in player's stack.")

    if (amount >= minRaise)
      return Illegal(s"Raise cannot be smaller than $minRaise.")

    Legal
  }

  private def canCall: ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < lastBetSize)
      return Illegal("Not enough chips to call.")

    Legal

  }

  private def canFold: ActionValidation = Legal

  private def canGoAllIn(amount: Int): ActionValidation = Legal
}