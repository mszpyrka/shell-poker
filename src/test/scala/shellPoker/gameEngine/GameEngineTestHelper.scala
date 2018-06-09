package shellPoker.gameEngine

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

    //initializing pokerTable
    pokerTableMock = new PokerTable(testGameSettings.seatsNumber)

    //initializing players
    playersMock = (for(_ <- 0 until testGameSettings.seatsNumber) 
      yield new Player(new ChipStack(testGameSettings.startingStack))).toList

    //adding them to pokerTable
    for(i <- 0 until testGameSettings.seatsNumber) pokerTableMock.seats(i).addPlayer(playersMock(i))

    pokerTableMock.positionManager.setDealerButton(pokerTableMock.seats(0))

    new GameState(
      pokerTableMock,
      testGameSettings.smallBlindValue,
      testGameSettings.bigBlindValue,
      null,
      null,
      0,
      100,
      200,
      -1
    )
  }

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
      pokerTableMock,
      testGameSettings.smallBlindValue,
      testGameSettings.bigBlindValue,
      null,
      null,
      1,
      100,
      200,
      -1
    )

  }

}