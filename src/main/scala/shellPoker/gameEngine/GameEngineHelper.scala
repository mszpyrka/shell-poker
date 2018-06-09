package shellPoker.gameEngine

import scala.util.Random.shuffle

object GameEngineHelper{

  def getStartingGameState(gameSettings: GameSettings, playersNumber: Int) = {
    //initializing pokerTable
    val table: PokerTable = new PokerTable(gameSettings.seatsNumber)

    //initializing players(full table )
    val players: List[Player] = (for(_ <- 0 until playersNumber) 
      yield new Player(new ChipStack(gameSettings.startingStack))).toList

    val seatIndecies: List[Int] = shuffle((0 until gameSettings.seatsNumber).toList).take(playersNumber)

    //adding them to pokerTable
    for(i <- 0 until playersNumber) 
      table.seats(seatIndecies(i)).addPlayer(players(i))

    table.positionManager.pickRandomPositions()

    new GameState(
      table = table,
      smallBlindValue = gameSettings.smallBlindValue,
      bigBlindValue = gameSettings.bigBlindValue,
      actionTaker = null,
      roundEndingSeat = null,
      currentBettingRound = 0,
      minRaise = -1,
      minBet = -1,
      lastBetSize = -1
    )
  }
}