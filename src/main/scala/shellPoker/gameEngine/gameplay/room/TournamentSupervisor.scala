package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand._
import shellPoker.gameEngine.gameplay._
import shellPoker.gameEngine.player.PlayerId


/* Responsible for running local test tournament style game. */
class TournamentSupervisor(
    gameSettings: GameSettings,
    remoteCommunicator: RemoteCommunicator
  ) extends RoomSupervisor(gameSettings, remoteCommunicator) {

  /** Gets players that joined the game during some hand. */
  protected def getPendingPlayers: List[PlayerId] = Nil

  /** Updates communicator. */
  override protected def updateCommunicator(): Unit = ()
}