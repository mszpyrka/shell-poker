package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand._
import shellPoker.gameEngine.table._
import shellPoker.gameEngine.gameplay._


/* Responsible for running local test tournament style game. */
class TournamentSupervisor(gameSettings: GameSettings) extends RoomSupervisor(gameSettings) {

  /** Gets players that joined the game during some hand. */
  protected def getPendingPlayers(): List[PlayerId] = Nil

  /** Gets chosen hand supervisor for this room supervisor. */
  protected def getHandSupervisor(gameState: GameState): HandSupervisor = new LocalTestSupervisor(gameState)
}