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
class HandSupervisor(initState: GameState, communicator: Communicator) {

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

    // Shuffles the deck and deals hole cards to each player.
    table.dealer.shuffle()
    table.dealer.proceedWithAction()

    var possibleAction: Boolean = true
    var allPlayersFoldedToBet = false

    communicator.logGameStatus(gameState)

    while (possibleAction && !allPlayersFoldedToBet && table.dealer.status != Showdown) {

      communicator.logHandStatus(gameState)

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
    }

    val handEndingType = {

      if (table.dealer.status == Showdown) ClassicShowdown
      else if (allPlayersFoldedToBet) AllFoldedToBet
      else PlayersAllIn
    }

    val endingHelper: HandEndingHelper = HandEndingHelper.getHelper(gameState, handEndingType)

    // Final actions needed to be taken before the winners are picked.
    endingHelper.proceedWithFinalActions()

    communicator.logShowdownStatus(gameState)

    communicator.logHandStatus(gameState)

    val handResults: CompleteHandResults = endingHelper.calculateHandResults()

    applyHandResults(handResults)

    communicator.logEndingStatus(handResults)

    gameState
  }

  private def runBettingRound(): Unit = {

    // if current action taker == null that means that the round has ended
    while(actionManager.actionTaker != null){

      val currentAction: Action = getPlayerAction(actionManager.actionTaker)
      communicator.logAction(actionManager.actionTaker.id, currentAction)
      actionManager.applyAction(currentAction)

      communicator.logHandStatus(gameState)
    }
  }

  private def getPlayerAction(actionTaker: Player): Action = {

    var playerAction: Action = communicator.requestAction(actionTaker.id)
    var validation: ActionValidation = actionManager.validateAction(playerAction)

    while(validation != Legal){

      communicator.logValidation(actionTaker.id, validation)
      playerAction = communicator.requestAction(actionTaker.id)
      validation = actionManager.validateAction(playerAction)
    }

    playerAction
  }

  private def applyHandResults(results: CompleteHandResults): Unit = {

    for (singlePlayerResult <- results.results)
      singlePlayerResult.player.chipStack.addChips(singlePlayerResult.chipsWon)
  }
}
