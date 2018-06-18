package shellPoker.actors.server

import akka.actor.Actor
import shellPoker.gameEngine.gameplay._
import shellPoker.gameEngine.gameplay.hand._
import shellPoker.gameEngine.gameplay.room.RoomSupervisor


/* Responsible for running local test tournament style game. */
class TournamentSupervisorActor(
    gameSettings: GameSettings,
    communicator: Communicator
  ) extends Actor {

  private var registeredPlayers

  override def receive: Receive = {



  }

  private def runTournament(): Unit = {

    val supervisor = new TournamentSupervisorActor(gameSettings, communicator)
    supervisor.runTournament()
  }
}