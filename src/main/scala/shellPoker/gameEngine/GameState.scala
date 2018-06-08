package shellPoker.gameEngine


class GameState(
    var bigBlindValue: Int
    var currentActionTaker: TableSeat, 
    var roundEndingSeat: TableSeat,
    var currentBettingRound: Int,
    var minBet: Int, 
    var minRaise: Int, 
    var lastBetSize: Int,
    var table: PokerTable)