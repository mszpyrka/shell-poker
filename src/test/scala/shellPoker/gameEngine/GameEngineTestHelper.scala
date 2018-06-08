package shellPoker.gameEngine

object GameEngineTestHelper {

  var seatsAmont: Int = _
  var bigBllindValue: Int = _
  var initialChipCount: Int = _

  var pokerTableMock: PokerTable = _

  var playersMock: List[Player] = _

  var dealerButton: TableSeat = _
  var bigBlind: TableSeat = _
  var smallBlind: TableSeat = _

  var firstRoundGameState: GameState = new GameState()

  //seats: 0(dealer)- 500 chips, 1(smallBlind) - 1000 chips, 2(bigBlind) - 1000 chips, 3 - 1000 chips
  def getFirstRoundGameState() : GameState = {
    seatsAmont = 4
    bigBllindValue = 100
    initialChipCount = 1000

    pokerTableMock = new PokerTable(seatsAmont)

    playersMock = (for(_ <- 0 until seatsAmont) yield new Player(new ChipStack(initialChipCount))).toList
    playersMock(0).chipStack.removeChips(500)
    for(i <- 0 until seatsAmont) pokerTableMock.seats(i).addPlayer(playersMock(i))

    pokerTableMock.positionManager.pickRandomPositions()

    while(pokerTableMock.positionManager.dealerButton != pokerTableMock.seats(0))
      pokerTableMock.positionManager.movePositions()

    
    new GameState(
      bigBlindValue
      pokerTableMock.seats(3),
      pokerTableMock.seats(2),
      positionManager.dealerButton,
      positionManager.smallBlind,
      positionManager.bigBlind,
      1,
      200,
      100,
      100,
      pokerTableMock)
  }


  //seats: 0(dealer)- 500 chips, 1(smallBlind) - 1000 chips, 2(bigBlind) - 1000 chips, 3 - 1000 chips
  // def prepareActionManager(): ActionManager = {
  //   seatsAmont = 4
  //   bigBllindValue = 100
  //   initialChipCount = 1000

  //   pokerTableMock = new PokerTable(seatsAmont)

  //   playersMock = (for(_ <- 0 until seatsAmont) yield new Player(new ChipStack(initialChipCount))).toList
  //   playersMock(0).chipStack.removeChips(500)
  //   for(i <- 0 until seatsAmont) pokerTableMock.seats(i).addPlayer(playersMock(i))

  //   pokerTableMock.positionManager.pickRandomPositions()

  //   while(pokerTableMock.positionManager.dealerButton != pokerTableMock.seats(0))
  //     pokerTableMock.positionManager.movePositions()

  //   new ActionManager(bigBllindValue, pokerTableMock)
  // }

  



}