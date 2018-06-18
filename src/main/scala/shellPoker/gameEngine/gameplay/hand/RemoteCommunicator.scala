package shellPoker.gameEngine.gameplay.hand
import akka.actor.ActorRef
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.handEnding.CompleteHandResults
import shellPoker.gameEngine.player.PlayerId
import shellPoker.gameEngine.playerAction.Action

class RemoteCommunicator(val registeredPlayers: Map[PlayerId, ActorRef]) extends Communicator {


  override def requestAction(playerId: PlayerId): Action = {
    val playerActorRef: ActorRef = registeredPlayers(playerId)

    
  }

  override def logAction(playerId: PlayerId, action: Action): Unit = ???

  override def logGameStatus(gameState: GameState): Unit = ???

  override def logHandStatus(gameState: GameState): Unit = ???

  override def logShowdownStatus(gameState: GameState): Unit = ???

  override def logEndingStatus(results: CompleteHandResults): Unit = ???
}
