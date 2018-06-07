package shellPoker.gameEngine

import shellPoker.core.pokerHands.RoyalFlush

/** Responsible for managing single hand's betting rounds, which includes
  * extracting player that needs to make a decision,
  * validating player's actions
  * and applying them to change current state of the hand.
  * All action methods relate to the private _actionTaker field.
  *
  * @param bigBlindValue Value of the big blind.
  * @param table Reference to a poker table. 
  */
class ActionManager(
    bigBlindValue: Int,
    table: PokerTable) {

  private var currentBettingRound: Int = 0    //represents current betting round, 0 -> pre game, 4 -> post river
  private var roundEndingSeat: TableSeat = _  //represents last aggresive player, if it equals _actionTaker, the round ends
  private var _actionTaker: TableSeat = _     //represents current action taker, all action methods relate to it
  private var lastBetSize: Int = _            //represents last bet size
  private var minBet: Int = _                 //represents a min bet accroding to poker rules
  private var minRaise: Int = _               //represents a min raise according to poker rules


  /* Changes the internal state of the object according to the betting round. */
  def startNextRound(): Unit = {

    currentBettingRound += 1
    minBet = 2 * bigBlindValue
    minRaise = bigBlindValue

    if (currentBettingRound == 1) {

      roundEndingSeat = table.getNextActiveSeat(table.positionManager.bigBlind)
      _actionTaker = table.getNextActiveSeat(table.positionManager.bigBlind)
      lastBetSize = bigBlindValue
    }

    else {

      roundEndingSeat = table.getNextActiveSeat(table.positionManager.dealerButton)
      _actionTaker = table.getNextActiveSeat(table.positionManager.dealerButton)
      lastBetSize = 0
    }
  }


  /* Validates if the given action of the _actionTaker is legal or not. */
  def validateAction(action: Action): ActionValidation = {

    action match {
      case Bet(amount) => canBet(amount)
      case Raise(amount) => canRaise(amount)
      case Call => canCall
      case Check => canCheck
      case Fold => canFold
      case AllIn(amount) => canGoAllIn(amount)
    }
  }


  /* Returns reference to the current action taker. */
  def actionTaker: TableSeat = _actionTaker


  /* Changes the internal state of the object according to the
   * current action taker's action.
   * Applies necessary changes to player instance related to the taken action.
   */
  def applyAction(action: Action): Unit = {

    validateAction(action) match {

      case illegal: Illegal => throw IllegalActionException(illegal)
      case _ => ()
    }

    action match {

      case Bet(amount) => {

        minRaise = amount - lastBetSize
        minBet = amount + minRaise
        lastBetSize = amount
        roundEndingSeat = _actionTaker
        _actionTaker.player.setBetSize(amount)
      }

      case Raise(amount) => {

        minRaise = amount
        lastBetSize = lastBetSize + amount
        minBet = lastBetSize + amount
        roundEndingSeat = _actionTaker
        _actionTaker.player.setBetSize(lastBetSize)
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

        _actionTaker.player.goAllIn()
      }

      case Call => ()

      case Fold => _actionTaker.player.setFolded()

      case Check => ()
    }

    _actionTaker = nextActionTaker
  }

  /* Calculates next action taker, if it's equal to roundEnding seat
   * then it returns null and the round is at its ending.
   */
  private def nextActionTaker: TableSeat = {

    val nextActiveSeat = table.getNextActiveSeat(_actionTaker)
    if (nextActiveSeat == roundEndingSeat)
      return null

    nextActiveSeat
  }

  //Action validating methods below

  private def canCheck: ActionValidation = {

    if((lastBetSize == 0) ||
        (_actionTaker == table.positionManager.bigBlind && lastBetSize == bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }

  private def canBet(amount: Int): ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount < minBet)
      return Illegal(s"Bet size cannot be smaller than $minBet.")

    Legal
  }

  private def canRaise(amount: Int): ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < lastBetSize + amount)
      return Illegal("Not enough chips in player's stack.")

    if (amount < minRaise)
      return Illegal(s"Raise cannot be smaller than $minRaise.")

    Legal
  }

  private def canCall: ActionValidation = {

    if (_actionTaker.player.chipStack.chipCount +
        _actionTaker.player.currentBetSize < lastBetSize)
      return Illegal("Not enough chips to call.")

    if (canCheck == Legal)
      return Illegal("There is no bet for player to call.")

    Legal

  }

  private def canFold: ActionValidation = Legal

  private def canGoAllIn(amount: Int): ActionValidation = Legal
}