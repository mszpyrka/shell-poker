package shellPoker.gameEngine

import shellPoker.gameEngine.player.{ChipStack, Player}
import shellPoker.gameEngine.playerAction.ActionManager
import shellPoker.gameEngine.table.{PokerTable, TableSeat}

object GameEngineTestHelper {

  //some testing game settings
  var testGameSettings: GameSettings = new GameSettings(
      startingStack = 1000,
      seatsNumber = 4,
      smallBlindValue = 50,
      bigBlindValue = 100,
      decisionTime = 30
  )

  //variables needed to create GameState
  var pokerTableMock: PokerTable = _
  var actionTaker: TableSeat = _
  var roundEndingSeat: TableSeat = _
  var currentBettingRound: Int = _
  var minRaise: Int = _
  var minBet: Int = _
  var lastBetSize: Int = _

  //Needed to properly initialize PokerTable
  var playersMock: List[Player] = _




  //seats: 0(dealer)- 1000 chips, 1(smallBlind) - 1000 chips, 2(bigBlind) - 1000 chips, 3 - 1000 chips
  def preGameState: GameState = {
    val tmpGameState: GameState = GameEngineHelper.getStartingGameState(testGameSettings, testGameSettings.seatsNumber)
    tmpGameState.table.positionManager.setDealerButton(tmpGameState.table.seats(0))
    tmpGameState
  }


  //also a check scenario
  //seats: 0(dealer) - active 900, 1000, 1(smallBlind) - folded 950 , 2(bigBlind) -  active 900, 3 - active 900
  def preRound2State: GameState = {
    pokerTableMock = new PokerTable(testGameSettings.seatsNumber)

    //initializing players
    playersMock = (for(_ <- 0 until testGameSettings.seatsNumber) 
      yield new Player(new ChipStack(testGameSettings.startingStack))).toList

    //adding them to pokerTable
    for(i <- 0 until testGameSettings.seatsNumber) pokerTableMock.seats(i).addPlayer(playersMock(i))

    pokerTableMock.seats(1).player.chipStack.removeChips(50)
    pokerTableMock.seats(2).player.chipStack.removeChips(100)
    pokerTableMock.seats(3).player.chipStack.removeChips(100)

    pokerTableMock.positionManager.setDealerButton(pokerTableMock.seats(0))

    pokerTableMock.seats(1).player.setFolded()


    new GameState(
      table = pokerTableMock,
      smallBlindValue = testGameSettings.smallBlindValue,
      bigBlindValue = testGameSettings.bigBlindValue,
      actionTaker = null,
      roundEndingPlayer = null,
      currentBettingRound = 1,
      minRaise = 100,
      minBet = 200,
      lastBetSize = -1
    )

  }

  def legalCheckScenario1: GameState = {
    val tmpActionManager = new ActionManager(preRound2State)
    tmpActionManager.startNextBettingRound()
    val tmpGameState = tmpActionManager.gameState
    tmpGameState
  }

  def legalCheckScenario2: GameState = {
    pokerTableMock = new PokerTable(testGameSettings.seatsNumber)

    //initializing players
    playersMock = (for(_ <- 0 until testGameSettings.seatsNumber) 
      yield new Player(new ChipStack(testGameSettings.startingStack))).toList

    //adding them to pokerTable
    for(i <- 0 until testGameSettings.seatsNumber) pokerTableMock.seats(i).addPlayer(playersMock(i))

    pokerTableMock.positionManager.setDealerButton(pokerTableMock.seats(0))

    new GameState(
      table = pokerTableMock,
      smallBlindValue = testGameSettings.smallBlindValue,
      bigBlindValue = testGameSettings.bigBlindValue,
      actionTaker = pokerTableMock.seats(2),
      roundEndingPlayer = pokerTableMock.seats(3),
      currentBettingRound = 1,
      minRaise = 100,
      minBet = 200,
      lastBetSize = testGameSettings.bigBlindValue
    )
  }


  def illegalCallScenario1: GameState = {
    pokerTableMock = new PokerTable(testGameSettings.seatsNumber)

    //initializing players
    playersMock = (for(_ <- 0 until testGameSettings.seatsNumber) 
      yield new Player(new ChipStack(testGameSettings.startingStack))).toList

    //adding them to pokerTable
    for(i <- 0 until testGameSettings.seatsNumber) pokerTableMock.seats(i).addPlayer(playersMock(i))

    pokerTableMock.positionManager.setDealerButton(pokerTableMock.seats(0))

    new GameState(
      table = pokerTableMock,
      smallBlindValue = testGameSettings.smallBlindValue,
      bigBlindValue = testGameSettings.bigBlindValue,
      actionTaker = pokerTableMock.seats(2),
      roundEndingPlayer = pokerTableMock.seats(3),
      currentBettingRound = 1,
      minRaise = 100,
      minBet = 200,
      lastBetSize = 1100
    )
  }

  def illegalCallScenario2: GameState = legalCheckScenario2


  def illegalBetScenario: GameState = {
    val tmpActionManager = new ActionManager(preGameState)
    tmpActionManager.startNextBettingRound()
    val tmpGameState = tmpActionManager.gameState

    tmpGameState.actionTaker.player.setBetSize(50)

    tmpGameState
  }

}