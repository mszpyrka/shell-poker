package shellPoker.gameEngine.gameplay.hand

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding._
import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.playerAction.{Action, ActionManager, ActionValidation, Legal}
import shellPoker.gameEngine.table.{River, Showdown}

/** Supervisor of a single hand.
  *
  * @param initState Initial state of the hand.
  */
abstract class HandSupervisor(initState: GameState) extends SupervisorCommunicator {

  private val actionManager: ActionManager = new ActionManager(initState)
  def gameState: GameState = actionManager.gameState

  /** Plays a single hand, ending when some people win chips */
  def playSingleHand(): GameState = {

    val table = gameState.table

    // Posting blinds
    if(!table.smallBlind.isEmpty)
      table.smallBlind.player.postBlind(gameState.smallBlindValue)

    if(!table.bigBlind.isEmpty)
      table.bigBlind.player.postBlind(gameState.bigBlindValue)


    // Shuffling the deck and dealing hole cards
    table.dealer.clearAllCards()
    table.dealer.proceedWithAction()


    logHandStatus(gameState)

    var possibleAction: Boolean = true
    var allPlayersFoldedToBet = false

    while (possibleAction && !allPlayersFoldedToBet && table.dealer.status != Showdown) {

      // Standard betting round elements
      actionManager.startNextBettingRound()
      runBettingRound()
      table.potManager.collectBets()

      if (table.playersInGameNumber <= 1)
        allPlayersFoldedToBet = true

      if (table.activePlayersNumber <= 1)
        possibleAction = false

      // Proceeds every time there is any further action possibility
      // or there are some players in game on the river.
      if (possibleAction || (!allPlayersFoldedToBet && table.dealer.status == River))
        table.dealer.proceedWithAction()

      // After betting round
      logHandStatus(gameState)
    }


    val handEndingType = {
      if (table.dealer.status == Showdown)
        ClassicShowdown

      else if (allPlayersFoldedToBet)
        AllFoldedToBet

      else
        PlayersAllIn
    }

    val endingHelper: HandEndingHelper = HandEndingHelper.getHelper(gameState, handEndingType)

    // Final actions needed to be ta
    endingHelper.proceedWithFinalActions()
    val handResults: CompleteHandResults = endingHelper.calculateHandResults()

    applyHandResults(handResults)

    logHandStatus(gameState)

    gameState
  }

  private def runBettingRound(): Unit = {

    //if current action taker == null that means that the round has ended
    while(actionManager.actionTaker != null){

      //getting action from current action taker
      val currentAction: Action = getPlayerAction(actionManager.actionTaker)

      logAction(actionManager.actionTaker, currentAction)

      //apply that action to the state fo the action manager
      actionManager.applyAction(currentAction)
    }
  }

  private def getPlayerAction(actionTaker: Player): Action = {

    //get initial player action
    var playerAction: Action = requestAction(actionTaker)

    //get initial action validation for this action
    var actionLegalness: ActionValidation = actionManager.validateAction(playerAction)

    //keep requesting for legal action
    while(actionLegalness != Legal){

      //sending illegal message info to the player actor
      logActionValidation(actionTaker, actionLegalness)

      //getting the action again
      playerAction = requestAction(actionTaker)

      //get the action validation
      actionLegalness = actionManager.validateAction(playerAction)
    }

    playerAction
  }

  private def applyHandResults(results: CompleteHandResults): Unit = {

    for (singlePlayerResult <- results.results)
      singlePlayerResult.player.chipStack.addChips(singlePlayerResult.chipsWon)
  }
}
