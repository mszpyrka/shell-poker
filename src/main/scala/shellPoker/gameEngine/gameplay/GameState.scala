package shellPoker.gameEngine.gameplay

import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.table.PokerTable

object GameState {
  def getInitGameState(gameSettings: GameSettings): GameState = 
    new GameState(
      table = new PokerTable(gameSettings.seatsNumber),
      smallBlindValue = gameSettings.smallBlindValue,
      bigBlindValue = gameSettings.bigBlindValue,
      actionTaker = null,
      roundEndingPlayer = null,
      currentBettingRound = 0,
      minRaise = -1,
      minBet = -1,
      lastBetSize = -1
    )
}


/** Represents game state at some particular time.
  *
  * @param table Holds the reference to a poker table instance.
  * @param smallBlindValue Holds the value of small blind.
  * @param bigBlindValue Holds the value of big blind.
  * @param actionTaker Represents the seat of current action taker.
  * @param roundEndingPlayer Represents the seat of last aggressive player,
  *                        if it is equal to gameState.actionTaker, the round should end.
  * @param currentBettingRound Represents current betting round, 0 -> pre-game, 1 -> pre-flop,
  *                            2 -> flop, 3 -> turn, 4 -> river
  * @param minRaise Represents the minimum legal raise amount according to no-limit hold'em rules.
  * @param minBet Represents minimum legal bet value according to no-limit hold'em rules.
  * @param lastBetSize Represents last bet size.
  */
class GameState(
    val handNumber: Int = 0,
    val table: PokerTable,
    val smallBlindValue: Int,
    val bigBlindValue: Int,
    val actionTaker: Player,
    val roundEndingPlayer: Player,
    val currentBettingRound: Int,
    val minRaise: Int,
    val minBet: Int,
    val lastBetSize: Int) {


  /** Creates new GameState instance.
    * Useful for applying partial modifications to certain state.
    */
  def getModified(
      handNumber: Int = this.handNumber,
      table: PokerTable = this.table,
      smallBlindValue: Int = this.smallBlindValue,
      bigBlindValue: Int = this.bigBlindValue,
      actionTaker: Player = this.actionTaker,
      roundEndingPlayer: Player = this.roundEndingPlayer,
      currentBettingRound: Int = this.currentBettingRound,
      minRaise: Int = this.minRaise,
      minBet: Int = this.minBet,
      lastBetSize: Int = this.lastBetSize): GameState = {

    new GameState(
      handNumber = handNumber,
      table = table,
      smallBlindValue = smallBlindValue,
      bigBlindValue = bigBlindValue,
      actionTaker = actionTaker,
      roundEndingPlayer = roundEndingPlayer,
      currentBettingRound = currentBettingRound,
      minRaise = minRaise,
      minBet = minBet,
      lastBetSize = lastBetSize)
  }
}
    