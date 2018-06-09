package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for BettingManager class. */
class ActionManagerTest extends FunSuite {


  var actionManagerMock: ActionManager = _

  test("startNextBettingRound should set gameState properly"){
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)

    actionManagerMock.startNextBettingRound()

    val gameStateRound1 = actionManagerMock.gameState

    assert(gameStateRound1.smallBlindValue === 50)
    assert(gameStateRound1.bigBlindValue === 100)
    assert(gameStateRound1.actionTaker === gameStateRound1.table.seats(3))
    assert(gameStateRound1.roundEndingSeat === gameStateRound1.table.seats(3))
    assert(gameStateRound1.currentBettingRound === 1)
    assert(gameStateRound1.minRaise === 100)
    assert(gameStateRound1.minBet === 200)
    assert(gameStateRound1.lastBetSize === 100)

    actionManagerMock = new ActionManager(GameEngineTestHelper.preRound2State)

    actionManagerMock.startNextBettingRound()

    val gameStateRound2 = actionManagerMock.gameState

    assert(gameStateRound2.smallBlindValue === 50)
    assert(gameStateRound2.bigBlindValue === 100)
    assert(gameStateRound2.actionTaker === gameStateRound2.table.seats(2)) 
    assert(gameStateRound2.roundEndingSeat === gameStateRound2.table.seats(2))
    assert(gameStateRound2.currentBettingRound === 2)
    assert(gameStateRound2.minRaise === 100)
    assert(gameStateRound2.minBet === 100)
    assert(gameStateRound2.lastBetSize === 0)
  }

  test("validateAction Fold should always be legal"){
    //some scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)
    actionManagerMock.startNextBettingRound()
    
    assert(actionManagerMock.validateAction(Fold) === Legal)

    //another scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.preRound2State)
    actionManagerMock.startNextBettingRound()

    assert(actionManagerMock.validateAction(Fold) === Legal)
  }

  test("validateAction AllIn should always be legal"){
    //some scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)
    actionManagerMock.startNextBettingRound()
    
    assert(actionManagerMock.validateAction(AllIn(actionManagerMock.actionTaker.player.chipStack.chipCount)) === Legal)

    //another scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.preRound2State)
    actionManagerMock.startNextBettingRound()

    assert(actionManagerMock.validateAction(AllIn(actionManagerMock.actionTaker.player.chipStack.chipCount)) === Legal)
  }

  test("validateAction Check should only be legal in 2 scenarios"){
    //first legal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.legalCheckScenario1)

    assert(actionManagerMock.validateAction(Check) === Legal)

    //second legal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.legalCheckScenario2)

    assert(actionManagerMock.validateAction(Check) === Legal)

    //some illegal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)
    actionManagerMock.startNextBettingRound()

    assert(actionManagerMock.validateAction(Check) === Illegal("Cannot check when a bet has been made."))
  }

  test("validateAction Call should only be illegal in 2 scenarios"){
    //first illegal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.illegalCallScenario1)

    assert(actionManagerMock.validateAction(Call) === Illegal("Not enough chips to call."))

    //second illegal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.illegalCallScenario2)

    assert(actionManagerMock.validateAction(Call) === Illegal("There is no bet for player to call."))

    //other scenarios are legal
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)
    actionManagerMock.startNextBettingRound()

    assert(actionManagerMock.validateAction(Call) === Legal)
  }

  test("validateAction Bet should only be illegal in 2 scenarios"){
    //first illegal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.illegalBetScenario)

    assert(actionManagerMock.validateAction(Bet(199)) === Illegal("Bet size cannot be smaller than 200."))

    //second illegal scnario
    assert(actionManagerMock.validateAction(Bet(1001)) === Illegal("Bet size is bigger than player's stack."))

    //some other legal scnarios
    assert(actionManagerMock.validateAction(Bet(200)) === Legal)
    assert(actionManagerMock.validateAction(Bet(201)) === Legal)
    assert(actionManagerMock.validateAction(Bet(500)) === Legal)
    assert(actionManagerMock.validateAction(Bet(1000)) === Legal)

    actionManagerMock = new ActionManager(GameEngineTestHelper.legalCheckScenario2)
    
    assert(actionManagerMock.validateAction(Bet(200)) === Legal)
    assert(actionManagerMock.validateAction(Bet(201)) === Legal)
    assert(actionManagerMock.validateAction(Bet(500)) === Legal)
    assert(actionManagerMock.validateAction(Bet(1000)) === Legal)
  }

  test("validateAction Raise should only be illegal in 2 scenarios"){
    //first illegal scenario
    actionManagerMock = new ActionManager(GameEngineTestHelper.illegalBetScenario)

    assert(actionManagerMock.validateAction(Raise(99)) === Illegal("Raise cannot be smaller than 100."))

    //second illegal scnario
    assert(actionManagerMock.validateAction(Raise(901)) === Illegal("Not enough chips in player's stack."))

    //some other legal scnarios
    assert(actionManagerMock.validateAction(Raise(100)) === Legal)
    assert(actionManagerMock.validateAction(Raise(101)) === Legal)
    assert(actionManagerMock.validateAction(Raise(500)) === Legal)
    assert(actionManagerMock.validateAction(Raise(900)) === Legal)

    actionManagerMock = new ActionManager(GameEngineTestHelper.legalCheckScenario2)
    
    assert(actionManagerMock.validateAction(Raise(100)) === Legal)
    assert(actionManagerMock.validateAction(Raise(101)) === Legal)
    assert(actionManagerMock.validateAction(Raise(500)) === Legal)
    assert(actionManagerMock.validateAction(Raise(900)) === Legal)
  }

 

  

}