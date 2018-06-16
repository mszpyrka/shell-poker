package shellPoker.gameEngine.gameplay

import shellPoker.gameEngine.player.Player
import shellPoker.gameEngine.playerAction.{Action, ActionValidation}

/** Defines api that allows communication between HandSupervisor and outer world.
  */
trait SupervisorCommunicator {

  def logHandStatus(gameState: GameState): Unit
  def requestAction(player: Player): Action
  def logAction(player: Player, action: Action)
  def logActionValidation(player: Player, validation: ActionValidation)
}
