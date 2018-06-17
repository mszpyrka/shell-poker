package shellPoker.gameEngine.gameplay.hand

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.CompleteHandResults
import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.playerAction.{Action, ActionValidation}

/** Defines api that allows communication between HandSupervisor and outer world.
  */
trait HandSupervisorCommunicator {

  def logGameStatus(gameState: GameState): Unit
  def logHandStatus(gameState: GameState): Unit
  def requestAction(player: Player): Action
  def logAction(player: Player, action: Action)
  def logActionValidation(player: Player, validation: ActionValidation)
  def logHandResults(results: CompleteHandResults, gameState: GameState)
}
