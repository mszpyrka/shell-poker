package shellPoker.gameEngine

/** Supervisor of a single hand.
  *
  * @param initState Settings of the game.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)
  private val showdownManager: ShowdownManager = new ShowdownManager

  def playSingleHand(): Unit = {

    val table = initState.table
    table.dealAllHoleCards()

    while(true){

      actionManager.startNextBettingRound()

      bettingRound()

      if(table.activePlayersNumber <= 1) {
        showdownManager.getShowdownStatuses()
        break; //ni mo brejka eksplicitee
      }

      supervisor ! showStatus

      table.dealer.dealNextStreet()
    }


  }

  private def bettingRound() = {

    while(actionManager.actionTaker != null){

      getPlayersAction(actionManager.actionTaker)

      actionManager.applyAction(Action)

      supervisor ! Action
    }
  }

  private def getPlayersAction(actionTaker: TableSeat, playerActor: PlayerActor): Action = {

    val playerAction: Action = requestAction(actionTaker)

    while(actionManager.validateAction(Action) != Legal){

      playerActor ! actionManager.validateAction(Action)

      playerAction = requestAction(actionTaker)
    }

    playerAction
  }



}
