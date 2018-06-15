package shellPoker.gameEngine

/** Supervisor of a single hand.
  *
  * @param initState Settings of the game.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)
  private val showdownManager: ShowdownManager = new ShowdownManager

  def playSingleHand(): Unit = {

    val hasEnded: Boolean = false

    val table = initState.table
    // table.dealAllHoleCards()

    var showDownStatuses: List[ShowdownStatus] = _

    while (hasEnded) {
      table.dealer.dealNextStreet() //now it should include dealing hole cards, maybe dealer.nextDealerAction ?

      actionManager.startNextBettingRound()

      bettingRound()

      supervisor ! showStatus // not sure if nessecary

      if (table.activePlayersNumber <= 1 || table.dealer.status == River){
        showDownStatuses = showdownManager.getShowdownStatuses(table, actionManager.roundEndingSeat)
        hasEnded = true
      }
    }

    supervisor ! showDownStatuses  //??? could be like that 
  }

  private def bettingRound() = {

    while(actionManager.actionTaker != null){

      val currentAction: Action = getPlayersAction(actionManager.actionTaker)

      actionManager.applyAction(currentAction)

      supervisor ! currentAction
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
