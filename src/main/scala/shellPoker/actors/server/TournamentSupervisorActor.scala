package shellPoker.actors.server

import akka.actor.{Actor, ActorRef, Props}
import shellPoker.actors.{Register, StartGame}
import shellPoker.gameEngine.gameplay._
import shellPoker.gameEngine.gameplay.hand._
import shellPoker.gameEngine.gameplay.room.{RoomSupervisor, TournamentSupervisor}
import shellPoker.gameEngine.player.PlayerId

object TournamentSupervisorActor {

  def props(settings: GameSettings): Props =
    Props(new TournamentSupervisorActor(settings))
}

/* Responsible for running local test tournament style game. */
class TournamentSupervisorActor(
    gameSettings: GameSettings,
  ) extends Actor {

  private var registeredPlayers: scala.collection.mutable.Map[PlayerId, ActorRef] =
    new scala.collection.mutable.HashMap[PlayerId, ActorRef]

  override def receive: Receive = {

    case "ping" => sender() ! "pong"

    case StartGame => runTournament()

    case reg: Register =>
      val newUser: ActorRef = sender()
      println(reg.playerId)
      registeredPlayers(reg.playerId) = newUser
      println("nowy user " + reg.playerId.name)
  }

  private def runTournament(): Unit = {

    val communicator = new RemoteCommunicator(registeredPlayers, gameSettings.decisionTime)
    val supervisor = new TournamentSupervisor(gameSettings, communicator)
    supervisor.run(registeredPlayers.keys.toList)
  }
}