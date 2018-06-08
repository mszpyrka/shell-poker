package shellPoker.gameEngine


class GameState(
    val bigBlindValue: Int          //holds value of the big blind.
    val table: PokerTable)          //holds reference to a poker table
    val actionTaker: TableSeat,     //represents current action taker
    val roundEndingSeat: TableSeat, //represents last aggresive player, if it equals gameState.actionTaker, the round ends
    val currentBettingRound: Int,   //represents current betting round, 0 -> pre game, 4 -> post river
    val minRaise: Int,              //represents a min raise according to poker rules
    val minBet: Int,                //represents a min bet accroding to poker rules
    val lastBetSize: Int,           //represents last bet size
    