package shellPoker.gameEngine.playerAction

import org.scalatest.FunSuite
import shellPoker.gameEngine.GameEngineTestHelper

/** Tests for BettingManager class. */
class ActionValidatorTest extends FunSuite {

  val actionValidator: ActionValidator = new ActionValidator


  test("validateAction Fold should always be legal"){
    //legal scenarios
    assert(actionValidator.validate(GameEngineTestHelper.getRound1StartingState, Fold) === Legal)
    assert(actionValidator.validate(GameEngineTestHelper.getRound2StartingState, Fold) === Legal)
  }


  test("validateAction AllIn should always be legal"){
    //legal scenarios
    assert(actionValidator.validate(GameEngineTestHelper.getRound1StartingState, Fold) === Legal)
    assert(actionValidator.validate(GameEngineTestHelper.getRound2StartingState, Fold) === Legal)
  }


  test("validateAction Check should only be legal in 2 scenarios"){
    //first legal scenario
    assert(actionValidator.validate(GameEngineTestHelper.legalCheckState1, Check) === Legal)

    //second legal scenario
    assert(actionValidator.validate(GameEngineTestHelper.legalCheckState2, Check) === Legal)

    //illegal scenario
    assert(actionValidator.validate(GameEngineTestHelper.getRound1StartingState, Check) === Illegal("Cannot check when a bet has been made."))
  }


  test("validateAction Call should only be illegal in 2 scenarios"){
    //first illegal scenario
    assert(actionValidator.validate(GameEngineTestHelper.illegalCallState1, Call) === Illegal("Not enough chips to call."))

    //second illegal scenario
    assert(actionValidator.validate(GameEngineTestHelper.illegalCallState2, Call) === Illegal("There is no bet for player to call."))
    assert(actionValidator.validate(GameEngineTestHelper.illegalCallState3, Call) === Illegal("There is no bet for player to call."))

    //legal scenario
    assert(actionValidator.validate(GameEngineTestHelper.getRound1StartingState, Call) === Legal)
  }

  // test("validateAction Bet should only be illegal in 2 scenarios"){
  //   //first illegal scenario
  //   actionManager = new ActionManager(GameEngineTestHelper.illegalBetScenario)

  //   assert(actionManager.validateAction(Bet(199)) === Illegal("Bet size cannot be smaller than 200."))

  //   //second illegal scnario
  //   assert(actionManager.validateAction(Bet(1001)) === Illegal("Bet size is bigger than player's stack."))

  //   //some other legal scnarios
  //   assert(actionManager.validateAction(Bet(200)) === Legal)
  //   assert(actionManager.validateAction(Bet(201)) === Legal)
  //   assert(actionManager.validateAction(Bet(500)) === Legal)
  //   assert(actionManager.validateAction(Bet(1000)) === Legal)

  //   actionManager = new ActionManager(GameEngineTestHelper.legalCheckScenario2)
    
  //   assert(actionManager.validateAction(Bet(200)) === Legal)
  //   assert(actionManager.validateAction(Bet(201)) === Legal)
  //   assert(actionManager.validateAction(Bet(500)) === Legal)
  //   assert(actionManager.validateAction(Bet(1000)) === Legal)
  // }

  // test("validateAction Raise should only be illegal in 2 scenarios"){
  //   //first illegal scenario
  //   actionManager = new ActionManager(GameEngineTestHelper.illegalBetScenario)

  //   assert(actionManager.validateAction(Raise(99)) === Illegal("Raise cannot be smaller than 100."))

  //   //second illegal scnario
  //   assert(actionManager.validateAction(Raise(901)) === Illegal("Not enough chips in player's stack."))

  //   //some other legal scnarios
  //   assert(actionManager.validateAction(Raise(100)) === Legal)
  //   assert(actionManager.validateAction(Raise(101)) === Legal)
  //   assert(actionManager.validateAction(Raise(500)) === Legal)
  //   assert(actionManager.validateAction(Raise(900)) === Legal)

  //   actionManager = new ActionManager(GameEngineTestHelper.legalCheckScenario2)
    
  //   assert(actionManager.validateAction(Raise(100)) === Legal)
  //   assert(actionManager.validateAction(Raise(101)) === Legal)
  //   assert(actionManager.validateAction(Raise(500)) === Legal)
  //   assert(actionManager.validateAction(Raise(900)) === Legal)
  // }


}