package shellPoker.gameEngine

import shellPoker.core.pokerHands.RoyalFlush

/** Responsible for managing single hand's betting rounds, which includes:
  * - extracting player that needs to make a decision;
  * - validating players' actions;
  * - applying players' actions to change current state of the game.
  *
  * All action methods relate to the gameState.actionTaker field.
  *
  * @param _gameState Initial state of the game.
  */
class ActionManager(private var _gameState: GameState) {


  def gameState: GameState = _gameState


  /* Changes the internal state of the object according to the betting round. */
  def startNextBettingRound(): Unit = {

    val table = gameState.table
    val smallBlindValue = gameState.smallBlindValue
    val bigBlindValue = gameState.bigBlindValue
    val currentBettingRound = gameState.currentBettingRound + 1

    val (roundEndingPlayer, actionTaker, lastBetSize, minBet, minRaise) = if (currentBettingRound == 1) {

      (table.getNextActiveSeat(table.bigBlind).player,
       table.getNextActiveSeat(table.bigBlind).player,
       bigBlindValue,
       bigBlindValue * 2,
       bigBlindValue)

    } else {

      (table.getNextActiveSeat(table.dealerButton).player,
       table.getNextActiveSeat(table.dealerButton).player,
       0,
       bigBlindValue,
       bigBlindValue)

    }

    _gameState = new GameState(
      handNumber = gameState.handNumber,
      table = table,
      smallBlindValue = smallBlindValue,
      bigBlindValue = bigBlindValue,
      currentBettingRound = currentBettingRound,
      minBet = minBet,
      minRaise = minRaise,
      roundEndingPlayer = roundEndingPlayer,
      actionTaker = actionTaker,
      lastBetSize = lastBetSize
    )
  }


  /* Validates if the given action of the gameState.actionTaker is legal or not. */
  def validateAction(action: Action): ActionValidation = {

    action match {
      case Bet(amount) => canBet(amount)
      case Raise(amount) => canRaise(amount)
      case Call => canCall
      case Check => canCheck
      case Fold => canFold
      case AllIn => canGoAllIn
    }
  }


  /* Returns reference to the current action taker. */
  def actionTaker: Player = gameState.actionTaker


  /* Changes the internal state of the object according to current action taker's action.
   * Applies necessary changes to player instance related to the taken action.
   */
  def applyAction(action: Action): Unit = {

    validateAction(action) match {

      case illegal: Illegal => throw IllegalActionException(illegal)
      case _ => ()
    }

    val unchanged = (
      gameState.minBet,
      gameState.minRaise,
      gameState.lastBetSize,
      gameState.roundEndingPlayer
    )

    val newActionTaker = nextActionTaker

    val (minBet: Int, minRaise: Int, lastBetSize: Int, roundEndingPlayer: Player) = action match {

      case Bet(amount) => {

        actionTaker.setBetSize(amount)

        val newMinRaise = amount - gameState.lastBetSize
        val newMinBet = amount + newMinRaise
        val newLastBetSize = amount
        val newRoundEndingPlayer = actionTaker

        (
          newMinBet,
          newMinRaise,
          newLastBetSize,
          newRoundEndingPlayer
        )
      }

      case Raise(amount) => {

        actionTaker.setBetSize(gameState.lastBetSize + amount)

        val newMinRaise = amount
        val newLastBetSize = gameState.lastBetSize + amount
        val newMinBet = newLastBetSize + amount
        val newRoundEndingPlayer = actionTaker

        (
          newMinBet,
          newMinRaise,
          newLastBetSize,
          newRoundEndingPlayer
        )
      }

      case AllIn => {

        actionTaker.goAllIn()

        val amount = actionTaker.currentBetSize

        if (amount > gameState.lastBetSize) {

          val raised: Int = amount - gameState.lastBetSize
          val newLastBetSize = amount

          val newMinRaise = if (raised > gameState.minRaise) raised else gameState.minRaise

          val newMinBet = newLastBetSize + newMinRaise
          val newRoundEndingPlayer = actionTaker

          (
            newMinBet,
            newMinRaise,
            newLastBetSize,
            newRoundEndingPlayer
          )
        }

        else
          unchanged
      }

      case Call => {

        actionTaker.setBetSize(gameState.lastBetSize)
        unchanged
      }

      case Fold => {

        actionTaker.setFolded()

        if (actionTaker == gameState.roundEndingPlayer) (
            gameState.minBet,
            gameState.minRaise,
            gameState.lastBetSize,
            newActionTaker
          )
        else
          unchanged
      }

      case Check => {

        unchanged
      }
    }

    _gameState = new GameState(
      handNumber = gameState.handNumber,
      table = gameState.table,
      smallBlindValue = gameState.smallBlindValue,
      bigBlindValue = gameState.bigBlindValue,
      currentBettingRound = gameState.currentBettingRound,

      // Those are the values that may change during single betting round.
      minBet = minBet,
      minRaise = minRaise,
      roundEndingPlayer = roundEndingPlayer,
      actionTaker = newActionTaker,
      lastBetSize = lastBetSize
    )
  }


  /* Calculates next action taker, if it's equal to roundEnding seat
   * then it returns null and the round is at its ending.
   */
  private def nextActionTaker: Player = {

    if(gameState.table.activePlayersNumber == 1)
      return null

    val nextActivePlayer = gameState.table.getNextActivePlayer(gameState.actionTaker)
    if (nextActivePlayer == gameState.roundEndingPlayer)
      return null

    nextActivePlayer
  }



  //Action validating methods below

  private def canCheck: ActionValidation = {

    if((gameState.lastBetSize == 0) ||
        (gameState.actionTaker.seat == gameState.table.positionManager.bigBlind &&
          gameState.lastBetSize == gameState.bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }

  private def canBet(amount: Int): ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
        gameState.actionTaker.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount < gameState.minBet)
      return Illegal("Bet size cannot be smaller than " + gameState.minBet + ".")

    Legal
  }

  private def canRaise(amount: Int): ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
        gameState.actionTaker.currentBetSize < gameState.lastBetSize + amount)
      return Illegal("Not enough chips in player's stack.")

    if (amount < gameState.minRaise)
      return Illegal("Raise cannot be smaller than " + gameState.minRaise + ".")

    Legal
  }

  private def canCall: ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
        gameState.actionTaker.currentBetSize < gameState.lastBetSize)
      return Illegal("Not enough chips to call.")

    if (canCheck == Legal)
      return Illegal("There is no bet for player to call.")

    Legal

  }

  private def canFold: ActionValidation = Legal

  private def canGoAllIn: ActionValidation = Legal
}