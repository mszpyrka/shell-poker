package shellPoker.gameEngine.playerAction

import shellPoker.gameEngine.GameState

/** Responsible for validating players' actions. */
class ActionValidator {


  /* Checks if the given action of the gameState.actionTaker is legal or not.
   * Returns proper message with ActionValidation in case of failed validation.
   */
  def validate(gameState: GameState, action: Action): ActionValidation = {

    action match {
      case Bet(amount) => canBet(gameState, amount)
      case Raise(amount) => canRaise(gameState, amount)
      case Call => canCall(gameState)
      case Check => canCheck(gameState)
      case Fold => canFold
      case AllIn => canGoAllIn
    }
  }


  // ===================================================================================================================
  // Actual validators for each possible action:
  // ===================================================================================================================


  /** A player can check either when no bet has been made in current betting round
    * or it is the first betting round, every other player folded or called the big blind,
    * and the player taking action is on the big blind position (in that case checking
    * closes the action).
    */
  private def canCheck(gameState: GameState): ActionValidation = {

    if((gameState.lastBetSize == 0) ||
      (gameState.actionTaker.seat == gameState.table.positionManager.bigBlind &&
        gameState.lastBetSize == gameState.bigBlindValue))
      return Legal

    Illegal("Cannot check when a bet has been made.")
  }


  /** Firstly the player cannot bet if the bet amount is bigger than player's chip stack.
    * If that is not the case, then the bet size must be equal to or greater than minBet value.
    *
    * @param amount The size of player's bet.
    */
  private def canBet(gameState: GameState, amount: Int): ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
      gameState.actionTaker.currentBetSize < amount)
      return Illegal("Bet size is bigger than player's stack.")

    if (amount < gameState.minBet)
      return Illegal("Bet size cannot be smaller than " + gameState.minBet + ".")

    Legal
  }


  /** In order to raise the player must raise by value at least the size of the
    * previous raise. In that case the raise value becomes new minRaise.
    *
    * The player that successfully raises becomes a new roundEndingPlayer.
    *
    * @param amount The value by which the player wants to raise the bet.
    */
  private def canRaise(gameState: GameState, amount: Int): ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
      gameState.actionTaker.currentBetSize < gameState.lastBetSize + amount)
      return Illegal("Not enough chips in player's stack.")

    if (amount < gameState.minRaise)
      return Illegal("Raise cannot be smaller than " + gameState.minRaise + ".")

    Legal
  }


  /** Player can call always when he has enough chips in his stack
    * and when there is any bet to call.
    */
  private def canCall(gameState: GameState): ActionValidation = {

    if (gameState.actionTaker.chipStack.chipCount +
      gameState.actionTaker.currentBetSize < gameState.lastBetSize)
      return Illegal("Not enough chips to call.")

    if (canCheck(gameState) == Legal)
      return Illegal("There is no bet for player to call.")

    Legal

  }


  /** There are no special restrictions on folding action.
    */
  private def canFold: ActionValidation = Legal


  /** There are no special restrictions on going all-in.
    */
  private def canGoAllIn: ActionValidation = Legal

}
