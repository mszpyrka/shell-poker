package shellPoker.gameEngine

import shellPoker.gameEngine.handEnding._

/** Supervisor of a single hand.
  *
  * @param initState Initial state of the hand.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)
  private val showdownManager: ShowdownManager = new ShowdownManager

  /** Plays a single hand, ending when some people win chips */
  def playSingleHand(): Unit = {

    val table = initState.table

    // Posting blinds
    if(!table.smallBlind.isEmpty)
      table.smallBlind.player.postBlind(initState.smallBlindValue)

    if(!table.bigBlind.isEmpty)
      table.bigBlind.player.postBlind(initState.bigBlindValue)


    // Shuffling the deck and dealing hole cards
    table.dealer.clearAllCards()
    table.dealer.proceedWithAction()


    var possibleAction: Boolean = true

    while (possibleAction && table.dealer.status != Showdown) {

      // Standard betting round elements
      actionManager.startNextBettingRound()
      runBettingRound(supervisor)
      table.potManager.collectBets()

      // Sending update info about current game state
      supervisor ! update

      if (table.activePlayersNumber <= 1 && table.dealer.status != River)
        possibleAction = false

      if (possibleAction)
        table.dealer.proceedWithAction()
    }


    val handEndingType = {
      if (table.dealer.status == Showdown)
        ClassicShowdown

      else if (table.playersInGameNumber <= 1)
        AllFoldedToBet

      else
        PlayersAllIn
    }

    val endingHelper: HandEndingHelper = HandEndingHelper.getHelper(table, handEndingType)

    // Final actions needed to be ta
    endingHelper.proceedWithFinalActions()
    endingHelper.calculateHandResults()

    supervisor ! finalStatus  //??? could be like that
  }

  private def runBettingRound(supervisor: RoomSupervisorActor) = {

    //if current action taker == null that means that the round has ended
    while(actionManager.actionTaker != null){
      //getting action from current action taker
      val currentAction: Action = getPlayerAction(actionManager.actionTaker)

      //apply that action to the state fo the action manager
      actionManager.applyAction(currentAction)

      //send the supervisor info about current action
      supervisor ! currentAction
    }
  }

  private def getPlayerAction(actionTaker: Player): Action = {

    //get player actor object corresponding to the current seat
    val playerActor: PlayerActor = ???

    //get initial player action
    var playerAction: Action = requestAction(playerActor)

    //get initial action validation for this action
    var actionLegalness: ActionValidation = actionManager.validateAction(playerAction)

    //keep requesting for legal action
    while(actionLegalness != Legal){

      //sending illegal message info to the player actor
      playerActor ! actionLegalness

      //getting the action again
      playerAction = requestAction(playerActor)

      //get the action validation
      actionLegalness = actionManager.validateAction(playerAction)
    }

    playerAction
  }

  /** Prompts the player actor to return Action object */
  private def requestAction(playerActor: PlayerActor): Action = ???
}
