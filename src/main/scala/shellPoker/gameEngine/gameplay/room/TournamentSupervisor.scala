package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand._
import shellPoker.gameEngine.gameplay._


/* Responsible for running local test tournament style game. */
class TournamentSupervisor(
    gameSettings: GameSettings,
    communicator: HandSupervisorCommunicator
  ) extends RoomSupervisor(gameSettings, communicator) {

  /** Gets players that joined the game during some hand. */
  protected def getPendingPlayers: List[PlayerId] = Nil
}