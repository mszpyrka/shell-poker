package shellPoker.gameEngine

import shellPoker.core.pokerHands.RoyalFlush

/** Responsible for managing single hand's betting rounds, which includes
  * extracting player that needs to make a decision,
  * validating player's actions
  * and applying them to change current state of the hand.
  * All action methods relate to the gameState.actionTaker field
  *
  * @param gameState gameState of the table at the moment of creation of the ActionManager
  */
class ActionManager(var gameState: gameState) {

  /* Changes the internal state of the object according to the betting round. */
  def startNextRound(): Unit = {

    gameState.currentBettingRound += 1
    gameState.minBet = 2 * gameState.bigBlindValue
    gameState.minRaise = gameState.bigBlindValue

    if (gameState.currentBettingRound == 1) {

      gameState.roundEndingSeat = gameState.table.getNextActiveSeat(gameState.table.positionManager.bigBlind)
      gameState.actionTaker = gameState.table.getNextActiveSeat(gameState.table.positionManager.bigBlind)
      gameState.lastBetSize = gameState.bigBlindValue
    }

    else {

      gameState.roundEndingSeat = gameState.table.getNextActiveSeat(gameState.table.positionManager.dealerButton)
      gameState.actionTaker = gameState.table.getNextActiveSeat(gameState.table.positionManager.dealerButton)
      gameState.lastBetSize = 0
    }
  }


  /* Validates if the given action of the gameState.actionTaker is legal or not. */
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
  def actionTaker: TableSeat = gameState.actionTaker


  /* Changes the internal state of the object according to the
   * current action taker's action.
   * Applies necessary changes to player instance related to the taken action.
   */
  def applyAction(action: Action): Unit = {

    validateAction(action) match {

      case illegal: Illegal => throw IllegalActionException(illegal)
      case _ => ()
    }

    var newGameState: GameState = _

    action match {

      case Bet(amount) => {

        newGameState = new GameState(
            gameState.bigBlindValue,
            gameState.table,
            gameState.actionTaker
            nextActionTaker,
            gameState.currentBettingRound,
            amount - gameState.lastBetSize,
            2 * amount - gameState.lastBetSize,
            amount)

        newGameState.actionTaker.player.setBetSize(amount)
      }

      case Raise(amount) => {

        //TODO
        gameState.minRaise = amount
        gameState.lastBetSize = gameState.lastBetSize + amount
        gameState.minBet = gameState.lastBetSize + amount
        gameState.roundEndingSeat = gameState.actionTaker
        gameState.actionTaker.player.setBetSize(gameState.lastBetSize)
      }

      case AllIn(amount) => {

        if (amount > gameState.lastBetSize) {

          val raised: Int = amount - gameState.lastBetSize
          gameState.lastBetSize = amount

          if (raised > gameState.minRaise)
            gameState.minRaise = raised

          gameState.minBet = gameState.lastBetSize + gameState.minRaise
          gameState.roundEndingSeat = gameState.actionTaker
        }

        gameState.actionTaker.player.goAllIn()
      }

      case Call => ()

      case Fold => gameState.actionTaker.player.setFolded()

      case Check => ()
    }

    gameState = newGameState
  }

  /* Calculates next action taker, if it's equal to roundEnding seat
   * then it returns null and the round is at its ending.
   */
  private def nextActionTaker: TableSeat = {

    val nextActiveSeat = gameState.table.getNextActiveSeat(gameState.actionTaker)
    if (nextActiveSeat == gameState.roundEndingSeat)
      return null

    nextActiveSeat
  }

  //Action validating methods below

  private def canCheck: ActionValidation = {

    if((gameState.lastBetSize == 0) ||
        (gameState.actionTaker == gameState.table.positionManager.bigBlind && gameState.lastBetSize == gameState.bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }

  private def canBet(amount: Int): ActionValidation = {

    if (gameState.actionTaker.player.chipStack.chipCount +
        gameState.actionTaker.player.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount < gameState.minBet)
      return Illegal(s"Bet size cannot be smaller than $gameState.minBet.")

    Legal
  }

  private def canRaise(amount: Int): ActionValidation = {

    if (gameState.actionTaker.player.chipStack.chipCount +
        gameState.actionTaker.player.currentBetSize < gameState.lastBetSize + amount)
      return Illegal("Not enough chips in player's stack.")

    if (amount < gameState.minRaise)
      return Illegal(s"Raise cannot be smaller than $gameState.minRaise.")

    Legal
  }

  private def canCall: ActionValidation = {

    if (gameState.actionTaker.player.chipStack.chipCount +
        gameState.actionTaker.player.currentBetSize < gameState.lastBetSize)
      return Illegal("Not enough chips to call.")

    if (canCheck == Legal)
      return Illegal("There is no bet for player to call.")

    Legal

  }

  private def canFold: ActionValidation = Legal

  private def canGoAllIn(amount: Int): ActionValidation = Legal
}