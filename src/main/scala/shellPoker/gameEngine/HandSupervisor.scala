package shellPoker.gameEngine

import shellPoker.gameEngine.handEnding._
import shellPoker.userCommunication.Parser

/** Supervisor of a single hand.
  *
  * @param initState Initial state of the hand.
  */
class HandSupervisor(val initState: GameState) {

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

      print("table: ")
      println(table.communityCards)

      // Standard betting round elements
      actionManager.startNextBettingRound()
      runBettingRound()
      table.potManager.collectBets()

      // Sending update info about current game state
      //supervisor ! update

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

    println("KONIEC")
    //supervisor ! finalStatus  //??? could be like that
  }

  private def runBettingRound(): Unit = {

    //if current action taker == null that means that the round has ended
    while(actionManager.actionTaker != null){
      //getting action from current action taker
      val currentAction: Action = getPlayerAction(actionManager.actionTaker)

      logAction(actionManager.actionTaker, currentAction, Legal)

      //apply that action to the state fo the action manager
      actionManager.applyAction(currentAction)

      //send the supervisor info about current action
      //supervisor ! currentAction
    }
  }

  private def getPlayerAction(actionTaker: Player): Action = {

    //get player actor object corresponding to the current seat
    //val playerActor: PlayerActor = ???

    //get initial player action
    var playerAction: Action = requestAction(actionTaker)

    //get initial action validation for this action
    var actionLegalness: ActionValidation = actionManager.validateAction(playerAction)

    //keep requesting for legal action
    while(actionLegalness != Legal){

      //sending illegal message info to the player actor
      logAction(actionTaker, playerAction, actionLegalness)

      //getting the action again
      playerAction = requestAction(actionTaker)

      //get the action validation
      actionLegalness = actionManager.validateAction(playerAction)
    }

    playerAction
  }

  /** Prompts the player actor to return Action object */
  private def requestAction(player: Player): Action = {

    print(player.name + "'s turn...")
    println(player.holeCards)
    var invalidInput = true
    var action: Action = null

    while(invalidInput) {

      invalidInput = false

      try {

        val command: String = scala.io.StdIn.readLine()
        action = Parser.stringToAction(command)
      } catch {
        case e: Exception => invalidInput = true
      }
    }

    action
  }



  private def logAction(player: Player, action: Action, valid: ActionValidation): Unit = {

    print(player.name + ": ")
    print(action)
    print(": ")
    println(valid)
  }

  private def retardedStatusLog(): Unit = {

    val state = actionManager.gameState

    ???

  }
}
