package shellPoker.gameEngine.playerAction

import org.scalatest.FunSuite
import shellPoker.gameEngine.GameEngineTestHelper

/** Tests for BettingManager class. */
class ActionManagerTest extends FunSuite {

  test("startNextBettingRound should set gameState properly"){
    var actionManager: ActionManager = new ActionManager(GameEngineTestHelper.preRound1State)

    //first round starting state checks
    actionManager.startNextBettingRound()

    val gameStateRound1 = actionManager.gameState

    assert(gameStateRound1.smallBlindValue === 50)
    assert(gameStateRound1.bigBlindValue === 100)
    assert(gameStateRound1.actionTaker === gameStateRound1.table.seats(3).player)
    assert(gameStateRound1.roundEndingPlayer === gameStateRound1.table.seats(3).player)
    assert(gameStateRound1.currentBettingRound === 1)
    assert(gameStateRound1.minRaise === 100)
    assert(gameStateRound1.minBet === 200)
    assert(gameStateRound1.lastBetSize === 100)


    //second round starting state checks
    actionManager = new ActionManager(GameEngineTestHelper.preRound2State)

    actionManager.startNextBettingRound()

    val gameStateRound2 = actionManager.gameState

    assert(gameStateRound2.smallBlindValue === 50)
    assert(gameStateRound2.bigBlindValue === 100)
    assert(gameStateRound2.actionTaker === gameStateRound2.table.seats(1).player) 
    assert(gameStateRound2.roundEndingPlayer === gameStateRound2.table.seats(1).player)
    assert(gameStateRound2.currentBettingRound === 2)
    assert(gameStateRound2.minRaise === 100)
    assert(gameStateRound2.minBet === 100)
    assert(gameStateRound2.lastBetSize === 0)
  }

  //validateAction and applyAction have their own dedicated tests
}