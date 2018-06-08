package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for BettingManager class. */
class ActionManagerTest extends FunSuite {

  var actionManagerMock: ActionManager = _

  
  //for later
  // test("startNextRound should work"){
  //   actionManagerMock = GameEngineTestHelper.prepareActionManager()

  //   actionManagerMock.startNextRound()

  //   assert(actionManagerMock.actionTaker === GameEngineTestHelper.pokerTableMock.seats(3))

  //   assert(actionManagerMock.actionManagerMock.actionTaker === GameEngineTestHelper.pokerTableMock.seats(3))
  // }

  test("validateAction should work"){
    actionManagerMock = GameEngineTestHelper.prepareActionManager()
    actionManagerMock.startNextRound()
    
    assert(actionManagerMock.validateAction(Fold) === Legal)

    assert(actionManagerMock.validateAction(AllIn(actionManagerMock.actionTaker.player.chipStack.chipCount)) === Legal)

    assert(actionManagerMock.validateAction(Call) === Legal)

    assert(actionManagerMock.validateAction(Check) === Illegal("Cannot check when a bet has been made."))

    assert(actionManagerMock.validateAction(Bet(200)) === Legal)
    assert(actionManagerMock.validateAction(Bet(199)) === Illegal("Bet size cannot be smaller than 200."))
    assert(actionManagerMock.validateAction(Bet(1000)) === Legal)
    assert(actionManagerMock.validateAction(Bet(1001)) === Illegal("Bet size is bigger than player's stack."))

    assert(actionManagerMock.validateAction(Raise(100)) === Legal)
    assert(actionManagerMock.validateAction(Raise(99)) === Illegal("Raise cannot be smaller than 100."))
    assert(actionManagerMock.validateAction(Raise(900)) === Legal)
    assert(actionManagerMock.validateAction(Raise(901)) === Illegal("Not enough chips in player's stack."))
  }

  test("applyAction Fold should work"){
    actionManagerMock = GameEngineTestHelper.prepareActionManager()
    actionManagerMock.startNextRound()

    actionManagerMock.applyAction(Fold)

    assert(actionManagerMock.actionTaker === GameEngineTestHelper.pokerTableMock.seats(0))

    assert(GameEngineTestHelper.pokerTableMock.seats(3).player.hasFolded)
  }

  test("applyAction Call should work"){
    actionManagerMock = GameEngineTestHelper.prepareActionManager()
    actionManagerMock.startNextRound()

    actionManagerMock.applyAction(Call)

    assert(actionManagerMock.actionTaker === GameEngineTestHelper.pokerTableMock.seats(0))
  }

  test("applyAction Bet should work"){
    actionManagerMock = GameEngineTestHelper.prepareActionManager()
    actionManagerMock.startNextRound()

    actionManagerMock.applyAction(Bet(200))

    //how to check kurwa round ending player? XD min Bet?

    
    assert(actionManagerMock.actionTaker === GameEngineTestHelper.pokerTableMock.seats(0))
  }

    // actionManagerMock.applyAction(Call)

    // assert(actionManagerMock.actionTaker === mockPokerTable.seats(0))

    // actionManagerMock.applyAction(Fold)

    // assert(actionManagerMock.actionTaker === mockPokerTable.seats(1))

    // actionManagerMock.applyAction(Call)

    // assert(actionManagerMock.actionTaker === mockPokerTable.seats(2))

    // assert(actionManagerMock.validateAction(Check) === Legal)

    // assert(actionManagerMock.validateAction(Call) === Illegal("There is no bet for player to call."))

    // actionManagerMock.applyAction(Check)

    // assert(actionManagerMock.actionTaker === null)
  // }

  
// /*
//   val newManager = new BettingManager

//   newManager.startNextRound

//   val actionTaker: TableSeat = newManager.actionTaker

//   if(actionTaker == null) 
//     if(newManager.table.activePlayersNumber != 1)
//       dealNextStreet
//       newManager.startNextRound
//     else
//       showdown



//   //GET RESPONSE
//   val testAction: Action = Bet(100)

//   ? 

//   val isLegal: ActionValidation = newManager.validateAction(testAction)

//   if(isLegal) {

//     newManager.proceedWithAction(testAction)
//   }
  

// */

}