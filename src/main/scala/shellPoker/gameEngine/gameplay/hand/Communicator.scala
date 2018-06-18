package shellPoker.gameEngine.gameplay.hand

import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.CompleteHandResults
import shellPoker.gameEngine.player.{Player, PlayerId}
import shellPoker.gameEngine.playerAction.{Action, ActionValidation}

/** Defines api that allows communication between HandSupervisor and outer world.
  */
trait Communicator {

  def requestAction(playerId: PlayerId): Action

  def logAction(playerId: PlayerId, action: Action)

  def logGameStatus(gameState: GameState): Unit
  def logHandStatus(gameState: GameState): Unit

  def logShowdownStatus(gameState: GameState): Unit

  def logEndingStatus(results: CompleteHandResults): Unit
}
