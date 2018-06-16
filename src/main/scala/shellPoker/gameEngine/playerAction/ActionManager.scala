package shellPoker.gameEngine.playerAction

import shellPoker.gameEngine._
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.player.Player

/** Responsible for changing game state according to actions taken by players.
  *
  * @param initialGameState Initial state of the game.
  */
class ActionManager(initialGameState: GameState) {

  private val actionValidator: ActionValidator = new ActionValidator
  private val actionApplier: ActionApplier = new ActionApplier
  private var _gameState = initialGameState
  def gameState: GameState = _gameState


  /* Changes the internal state of the object according to the betting round. */
  def startNextBettingRound(): Unit = {

    val table = gameState.table

    if (gameState.currentBettingRound == 0)
      _gameState = gameState.getModified(
        currentBettingRound = gameState.currentBettingRound + 1,
        roundEndingPlayer = table.getNextActiveSeat(table.bigBlind).player,
        actionTaker = table.getNextActiveSeat(table.bigBlind).player,
        lastBetSize = gameState.bigBlindValue,
        minBet = gameState.bigBlindValue * 2,
        minRaise = gameState.bigBlindValue)

    else
      _gameState = gameState.getModified(
        currentBettingRound = gameState.currentBettingRound + 1,
        roundEndingPlayer = table.getNextActiveSeat(table.dealerButton).player,
        actionTaker = table.getNextActiveSeat(table.dealerButton).player,
        lastBetSize = 0,
        minBet = gameState.bigBlindValue,
        minRaise = gameState.bigBlindValue
      )
  }


  /* Checks if the given action of the gameState.actionTaker is legal or not
   * and return validation status. */
  def validateAction(action: Action): ActionValidation =
    actionValidator.validate(gameState, action)


  /* Changes the internal state of the object according to current action taker's action.
   * Applies necessary changes to player instance related to the taken action.
   */
  def applyAction(action: Action): Unit = {

    validateAction(action) match {

      case illegal: Illegal => throw IllegalActionException(illegal)
      case _ => ()
    }

    val newState = actionApplier.apply(gameState, action)
    _gameState = newState
  }


  /* Returns reference to the current action taker. */
  def actionTaker: Player = gameState.actionTaker
}