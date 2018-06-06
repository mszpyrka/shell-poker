package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for BettingManager class. */
class ActionManagerTest extends FunSuite {

  val seatsAmont: Int = 4
  val bigBllindValue: Int = 100
  val initialChipCount: Int = 1000

  
  val mockPlayers: List[Player] = (for(_ <- 0 until seatsAmont) yield new Player(new ChipStack(initialChipCount))).toList
  mockPlayers(1).chipStack.removeChips(500)
  val mockPokerTable: PokerTable = new PokerTable(seatsAmont)

  for(i <- 0 until seatsAmont) mockPokerTable.seats(i).addPlayer(mockPlayers(i))

  val mockPositionManager: PositionManager = new PositionManager(mockPokerTable)

  mockPositionManager.pickRandomPositions()

  while(mockPositionManager.dealerButton != mockPokerTable.seats(0))
      mockPositionManager.movePositions()


  val mockActionManager: ActionManager = new ActionManager(
        bigBllindValue,
        mockPositionManager.dealerButton,
        mockPositionManager.bigBlind,
        mockPokerTable)



  test("Game should proceed accordingly to poker betting rules."){
    mockActionManager.startNextRound()

    assert(mockActionManager.actionTaker === mockPokerTable.seats(3))

    val actionTaker: TableSeat = mockActionManager.actionTaker

    assert(mockActionManager.validateAction(Fold) === Legal)

    assert(mockActionManager.validateAction(AllIn(actionTaker.player.chipStack.chipCount)) === Legal)

    assert(mockActionManager.validateAction(Call) === Legal)

    assert(mockActionManager.validateAction(Check) === Illegal("Cannot check when a bet has been made."))

    assert(mockActionManager.validateAction(Bet(200)) === Legal)
    assert(mockActionManager.validateAction(Bet(199)) === Illegal("Bet size cannot be smaller than 200."))
    assert(mockActionManager.validateAction(Bet(1000)) === Legal)
    assert(mockActionManager.validateAction(Bet(1001)) === Illegal("Bet size is bigger than player's stack."))

    assert(mockActionManager.validateAction(Raise(100)) === Legal)
    assert(mockActionManager.validateAction(Raise(99)) === Illegal("Raise cannot be smaller than 100."))
    assert(mockActionManager.validateAction(Raise(900)) === Legal)
    assert(mockActionManager.validateAction(Raise(901)) === Illegal("Not enough chips in player's stack."))


    mockActionManager.applyAction(Call)



    assert(mockActionManager.actionTaker === mockPokerTable.seats(0))

    mockActionManager.applyAction(Fold)

    assert(mockActionManager.actionTaker === mockPokerTable.seats(1))

    mockActionManager.applyAction(Call)

    assert(mockActionManager.actionTaker === mockPokerTable.seats(2))

    assert(mockActionManager.validateAction(Check) === Legal)

    assert(mockActionManager.validateAction(Call) === Illegal("There is no bet for player to call."))

    mockActionManager.applyAction(Check)

    assert(mockActionManager.actionTaker === null)

    //second round
    // seat(0) -> dealer(Folded), seat(1) -> smallBlind(active), 


  }
/*
  val newManager = new BettingManager

  newManager.startNextRound

  val actionTaker: TableSeat = newManager.actionTaker

  if(actionTaker == null) 
    if(newManager.table.activePlayersNumber != 1)
      dealNextStreet
      newManager.startNextRound
    else
      showdown



  //GET RESPONSE
  val testAction: Action = Bet(100)

  ? 

  val isLegal: ActionValidation = newManager.validateAction(testAction)

  if(isLegal) {

    newManager.proceedWithAction(testAction)
  }
  

*/

}