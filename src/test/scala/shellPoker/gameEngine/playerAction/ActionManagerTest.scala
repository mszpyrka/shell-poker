package shellPoker.gameEngine.playerAction

import org.scalatest.FunSuite
import shellPoker.gameEngine.GameEngineTestHelper

/** Tests for BettingManager class. */
class ActionManagerTest extends FunSuite {


  var actionManagerMock: ActionManager = _

  test("startNextBettingRound should set gameState properly"){
    actionManagerMock = new ActionManager(GameEngineTestHelper.preGameState)

    actionManagerMock.startNextBettingRound()

    val gameStateRound1 = actionManagerMock.gameState

    assert(gameStateRound1.smallBlindValue === 50)
    assert(gameStateRound1.bigBlindValue === 100)
    assert(gameStateRound1.actionTaker === gameStateRound1.table.seats(3).player)
    assert(gameStateRound1.roundEndingPlayer === gameStateRound1.table.seats(3).player)
    assert(gameStateRound1.currentBettingRound === 1)
    assert(gameStateRound1.minRaise === 100)
    assert(gameStateRound1.minBet === 200)
    assert(gameStateRound1.lastBetSize === 100)

    actionManagerMock = new ActionManager(GameEngineTestHelper.preRound2State)

    actionManagerMock.startNextBettingRound()

    val gameStateRound2 = actionManagerMock.gameState

    assert(gameStateRound2.smallBlindValue === 50)
    assert(gameStateRound2.bigBlindValue === 100)
    assert(gameStateRound2.actionTaker === gameStateRound2.table.seats(2).player) 
    assert(gameStateRound2.roundEndingPlayer === gameStateRound2.table.seats(2).player)
    assert(gameStateRound2.currentBettingRound === 2)
    assert(gameStateRound2.minRaise === 100)
    assert(gameStateRound2.minBet === 100)
    assert(gameStateRound2.lastBetSize === 0)
  }
}