package shellPoker.gameEngine


/** Represents game state at some particular time.
  *
  * @param table Holds the reference to a poker table instance.
  * @param smallBlindValue Holds the value of small blind.
  * @param bigBlindValue Holds the value of big blind.
  * @param actionTaker Represents the seat of current action taker.
  * @param roundEndingSeat Represents the seat of last aggressive player,
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
    val actionTaker: TableSeat,
    val roundEndingSeat: TableSeat,
    val currentBettingRound: Int,
    val minRaise: Int,
    val minBet: Int,
    val lastBetSize: Int)
    