package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for BettingManager class. */
class BettingManagerTest extends FunSuite {


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
  



}