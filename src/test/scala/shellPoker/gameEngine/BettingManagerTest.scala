package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for BettingManager class. */
class BettingManagerTest extends FunSuite {

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


  val mockBettingManager: BettingManager = new BettingManager(
        bigBllindValue, 
        mockPositionManager.dealerButton, 
        mockPositionManager.bigBlind, 
        mockPokerTable)



  test("Game should proceed accordingly to poker betting rules."){
    mockBettingManager.startNextRound()

    assert(mockBettingManager.actionTaker === mockPokerTable.seats(3))

    val actionTaker: TableSeat = mockBettingManager.actionTaker

    assert(mockBettingManager.validateAction(Fold) === Legal)

    assert(mockBettingManager.validateAction(AllIn(actionTaker.player.chipStack.chipCount)) === Legal)

    assert(mockBettingManager.validateAction(Call) === Legal)

    assert(mockBettingManager.validateAction(Check) === Illegal("Cannot check when a bet has been made."))

    assert(mockBettingManager.validateAction(Bet(200)) === Legal)
    assert(mockBettingManager.validateAction(Bet(199)) === Illegal("Bet size cannot be smaller than 200."))
    assert(mockBettingManager.validateAction(Bet(1000)) === Legal)
    assert(mockBettingManager.validateAction(Bet(1001)) === Illegal("Bet size is bigger than player's stack."))

    assert(mockBettingManager.validateAction(Raise(100)) === Legal)
    assert(mockBettingManager.validateAction(Raise(99)) === Illegal("Raise cannot be smaller than 100."))
    assert(mockBettingManager.validateAction(Raise(900)) === Legal)
    assert(mockBettingManager.validateAction(Raise(901)) === Illegal("Not enough chips in player's stack."))
    
    
    mockBettingManager.proceedWithAction(Call)

    

    assert(mockBettingManager.actionTaker === mockPokerTable.seats(0))

    mockBettingManager.proceedWithAction(Fold)

    assert(mockBettingManager.actionTaker === mockPokerTable.seats(1))

    mockBettingManager.proceedWithAction(Call)

    assert(mockBettingManager.actionTaker === mockPokerTable.seats(2))

    assert(mockBettingManager.validateAction(Check) === Legal)

    assert(mockBettingManager.validateAction(Call) === Illegal("There is no bet for player to call."))

    mockBettingManager.proceedWithAction(Check)

    assert(mockBettingManager.actionTaker === null)

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