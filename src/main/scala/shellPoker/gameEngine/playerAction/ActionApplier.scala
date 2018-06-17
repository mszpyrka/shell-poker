package shellPoker.gameEngine.playerAction

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.player.Player

/** Responsible for applying players' actions to given game state. */
class ActionApplier {


  /* Applies requested action to given game state and returns new modified state. */
  def apply(gameState: GameState, action: Action): GameState = {

    action match {
      case Bet(amount) => applyBet(gameState, amount)
      case Raise(amount) => applyRaise(gameState, amount)
      case Call => applyCall(gameState)
      case Check => applyCheck(gameState)
      case Fold => applyFold(gameState)
      case AllIn => applyAllIn(gameState)
    }
  }


  // ===================================================================================================================
  // Actual appliers for each possible action:
  // ===================================================================================================================


  /** Betting player's stack is decreased and currentBet is increased accordingly to bet amount.
    * Minimum bet and raise values and lastBetSize are adjusted.
    *
    * The player that successfully bets becomes a new roundEndingPlayer.
    *
    * @param amount Player's bet size.
    */
  private def applyBet(gameState: GameState, amount: Int): GameState = {

    val actionTaker = gameState.actionTaker
    actionTaker.setBetSize(amount)

    val newMinRaise = amount - gameState.lastBetSize
    val newMinBet = amount + newMinRaise
    val newLastBetSize = amount

    // Some game state values must be applied to ensure that proper next action taker is found.
    val midState = gameState.getModified(
      roundEndingPlayer = actionTaker,
      minBet = newMinBet,
      minRaise = newMinRaise,
      lastBetSize = newLastBetSize)

    midState.getModified(actionTaker = getNextActionTaker(midState))
  }


  /** Raising player's stack is decreased and currentBet is increased accordingly to raise amount.
    * Minimum bet and raise values and lastBetSize are adjusted.
    *
    * The player that successfully raises becomes a new roundEndingPlayer.
    *
    * @param amount Player's raise value.
    */
  private def applyRaise(gameState: GameState, amount: Int): GameState = {

    val actionTaker = gameState.actionTaker
    actionTaker.setBetSize(gameState.lastBetSize + amount)

    val newMinRaise = amount
    val newLastBetSize = gameState.lastBetSize + amount
    val newMinBet = newLastBetSize + amount

    // Some game state values must be applied to ensure that proper next action taker is found.
    val midState = gameState.getModified(
      roundEndingPlayer = actionTaker,
      minBet = newMinBet,
      minRaise = newMinRaise,
      lastBetSize = newLastBetSize
    )

    midState.getModified(actionTaker = getNextActionTaker(midState))
  }


  /** When player goes all in, whole his stack is posted to currentBet.
    * If all-in value is bigger than previous bet, minBet, minRaise
    * and lastBetSize are adjusted properly.
    *
    * If the player set new bet size by going all-in, he becomes a new roundEndingPlayer.
    */
  private def applyAllIn(gameState: GameState): GameState = {

    val actionTaker = gameState.actionTaker
    actionTaker.goAllIn()
    val amount = actionTaker.currentBetSize

    if (amount > gameState.lastBetSize) {

      val raised: Int = amount - gameState.lastBetSize
      val newLastBetSize = amount
      val newMinRaise = if (raised > gameState.minRaise) raised else gameState.minRaise
      val newMinBet = newLastBetSize + newMinRaise

      // Some game state values must be applied to ensure that proper next action taker is found.
      val midState = gameState.getModified(
        roundEndingPlayer = actionTaker,
        lastBetSize = newLastBetSize,
        minBet = newMinBet,
        minRaise = newMinRaise)

      midState.getModified(actionTaker = getNextActionTaker(midState))
    }

    else
      gameState.getModified(actionTaker = getNextActionTaker(gameState))
  }


  /** When player calls, his current bet is set to lastBetSize.
    * GameState only changes actionTaker player.
    */
  private def applyCall(gameState: GameState): GameState = {

    gameState.actionTaker.setBetSize(gameState.lastBetSize)
    gameState.getModified(actionTaker = getNextActionTaker(gameState))
  }


  /** Folding causes no special changes in the game state other than player's status change. */
  private def applyFold(gameState: GameState): GameState = {

    gameState.actionTaker.setFolded()
    gameState.getModified(actionTaker = getNextActionTaker(gameState))
  }


  /** Checking causes no special changes in the game state. */
  private def applyCheck(gameState: GameState): GameState =
    gameState.getModified(actionTaker = getNextActionTaker(gameState))



  // ===================================================================================================================
  // Some other helper methods:
  // ===================================================================================================================


  /** Finds next action taker. If it is equal to roundEndingSeat
    * then it returns null and the round is at its ending.
    */
  private def getNextActionTaker(gameState: GameState): Player = {

    if (gameState.table.playersInGameNumber <= 1)
      return null

    val actionTaker = gameState.actionTaker
    val nextActionTaker = gameState.table.getNextActivePlayer(actionTaker)
    val actionFinisher = gameState.roundEndingPlayer

    if (nextActionTaker == null)
      return null

    if (gameState.table.activePlayersNumber <= 1 && nextActionTaker.currentBetSize >= gameState.lastBetSize)
      return null

    val playersRange: List[Player] = gameState.table.getSeatsRange(
        actionTaker.seat,
        nextActionTaker.seat
      ).map(_.player)

    if (nextActionTaker == actionFinisher || playersRange.contains(actionFinisher))
      null

    else
      nextActionTaker
  }
}
