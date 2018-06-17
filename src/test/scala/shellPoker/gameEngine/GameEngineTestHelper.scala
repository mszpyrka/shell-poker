package shellPoker.gameEngine

import shellPoker.gameEngine.gameplay.{GameSettings, GameState}
import shellPoker.gameEngine.player.{ChipStack, Player}
import shellPoker.gameEngine.playerAction.ActionManager
import shellPoker.gameEngine.table.{PokerTable, TableSeat}

object GameEngineTestHelper {

  var testGameSettings: GameSettings = new GameSettings(
      startingStack = 1000,
      seatsNumber = 4,
      smallBlindValue = 50,
      bigBlindValue = 100,
      decisionTime = 30)

  //Helper method to set dealer button at given TableSeat
  def setDealerButton(table: PokerTable, seat: TableSeat): Unit = {
    table.positionManager.pickRandomPositions()
    while(table.dealerButton != seat)
      table.positionManager.pickRandomPositions()
  }


  // ===================================================================================================================
  // GameStates for testing ActionManager
  // ===================================================================================================================

  def preRound1State: GameState = {
    val initGameState: GameState = GameState.getEmptyTableState(testGameSettings)

    for(i <- 0 until testGameSettings.seatsNumber) initGameState.table.seats(i).createAndAddPlayer("test", testGameSettings.startingStack)

    setDealerButton(initGameState.table, initGameState.table.seats(0))
    
    initGameState
  }


  def preRound2State: GameState = {
    val initGameState: GameState = preRound1State

    initGameState.getModified(currentBettingRound = 1)
  }


  // ===================================================================================================================
  // GameStates for testing ActionValidator
  // ===================================================================================================================

  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind                                   - currentBet: 50, status: Active, chips: 950, 
    // 2(bigBlind)                                    - currentBet: 100, status: Active, chips: 900,
    // 3(actionTaker, roundEndingPlayer)              - currentBet: 0, status: Active, chips: 1000
  def getRound1StartingState: GameState = {
    val actionManager = new ActionManager(preRound1State)
    actionManager.startNextBettingRound()

    val gameState = actionManager.gameState

    gameState.table.smallBlind.player.postBlind(testGameSettings.smallBlindValue)
    gameState.table.bigBlind.player.postBlind(testGameSettings.bigBlindValue)

    gameState
  }


  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind, actionTaker, roundEndingPlayer)  - currentBet: 0, status: Active, chips: 1000, 
    // 2(bigBlind)                                    - currentBet: 0, status: Active, chips: 1000,
    // 3                                              - currentBet: 0, status: Active, chips: 1000
  def getRound2StartingState: GameState = {
    val actionManager = new ActionManager(preRound2State)
    actionManager.startNextBettingRound()
    
    actionManager.gameState
  }


  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind, actionTaker, roundEndingPlayer)  - currentBet: 0, status: Active, chips: 1000, 
    // 2(bigBlind)                                    - currentBet: 0, status: Active, chips: 1000,
    // 3                                              - currentBet: 0, status: Active, chips: 1000
  def legalCheckState1: GameState = getRound2StartingState


  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind                                   - currentBet: 50, status: Active, chips: 950, 
    // 2(bigBlind, actionTaker)                       - currentBet: 100, status: Active, chips: 900,
    // 3(roundEndingPlayer)                           - currentBet: 0, status: Active, chips: 1000
  def legalCheckState2: GameState = {
    val gameState = getRound1StartingState

    gameState.getModified(
      actionTaker = gameState.table.seats(2).player,
      roundEndingPlayer = gameState.table.seats(3).player
    )
  }


    //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind, roundEndingPlayer)               - currentBet: 1100, status: Active, chips: 100, 
    // 2(bigBlind, actionTaker)                       - currentBet: 0, status: Active, chips: 1000,
    // 3                                              - currentBet: 0, status: Active, chips: 1000
  def illegalCallState1: GameState = {
    val gameState:GameState = getRound2StartingState

    gameState.table.seats(1).player.chipStack.addChips(200)
    gameState.table.seats(1).player.chipStack.removeChips(1100)

    gameState.getModified(
      actionTaker = gameState.table.seats(2).player,
      lastBetSize = 1100
    )
  }

  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind, actionTaker, roundEndingPlayer)  - currentBet: 0, status: Active, chips: 1000, 
    // 2(bigBlind)                                    - currentBet: 0, status: Active, chips: 1000,
    // 3                                              - currentBet: 0, status: Active, chips: 1000
  def illegalCallState2: GameState = legalCheckState1


  //seats: 
    // 0(dealer)                                      - currentBet: 0, status: Active, chips: 1000, 
    // 1(smallBlind                                   - currentBet: 50, status: Active, chips: 950, 
    // 2(bigBlind, actionTaker)                       - currentBet: 100, status: Active, chips: 900,
    // 3(roundEndingPlayer)                           - currentBet: 0, status: Active, chips: 1000
  def illegalCallState3: GameState = legalCheckState2


  // def illegalBetScenario: GameState = {
  //   val tmpActionManager = new ActionManager(preGameState)
  //   tmpActionManager.startNextBettingRound()
  //   val tmpGameState = tmpActionManager.gameState

  //   tmpGameState.actionTaker.player.setBetSize(50)

  //   tmpGameState
  // }



  //======================================================================IGNORE=======================================================================
  // override def addPendingPlayer(pendingPlayer: PlayerId): Unit = {
  //       //If there is no such seat throw some kind of exception
  //       if(pendingPlayer.seatNumber >= gameSettings.seatsNumber)
  //           ???

  //       //Counting players that are sitting/have reserved same seat as pendingPlayer
  //       val sameSeatCount: Int =
  //           currentPlayers.count(_.seatNumber == pendingPlayer.seatNumber) +
  //           pendingPlayers.count(_.seatNumber == pendingPlayer.seatNumber)

  //       //If there are none, add pendingPlayer to pendingPlayers
  //       if(sameSeatCount == 0) pendingPlayers = pendingPlayer :: pendingPlayers

  //       //else throw some kind of exception
  //       else
  //           ???
  //   }

}