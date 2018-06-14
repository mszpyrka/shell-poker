package shellPoker.gameEngine

/** Supervisor of a single hand.
  *
  * @param initState Settings of the game.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)

  def playSingleHand(): Unit = {

    val table = initState.table
    table.dealAllHoleCards()

    actionManager.startNextBettingRound()

    bettingRound()

    possible showdown (po kazdym betting raundzie)

    table.dealFlop()

    supervisor ! showStatus

    actionManager.startNextBettingRound()

    bettingRound()

    table.dealTurn()


  }

  private def bettingRound() = {

    dopoki jest gra:
      nastepna decyzja
      apply decyzja
      wyslij decyzje do supervisora


  }

  private def getPlayersAction(actionTaker): Action = {

    dopoki jest niedobrze:
      pytaj o decyzje

    zwroc decyzje
  }



}
