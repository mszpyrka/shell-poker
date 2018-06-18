package shellPoker.gameEngine.gameplay.hand

import akka.actor.ActorRef
import shellPoker.actors._
import shellPoker.gameEngine.gameplay.GameState
import shellPoker.gameEngine.gameplay.status.{EndingStatus, GameStatus, HandStatus, ShowdownStatus}
import shellPoker.gameEngine.handEnding.CompleteHandResults
import shellPoker.gameEngine.player.PlayerId
import shellPoker.gameEngine.playerAction.Action

class RemoteCommunicator(registeredPlayers: Map[PlayerId, ActorRef], actionTimeout: Int) extends Communicator {


  override def requestAction(playerId: PlayerId): Action = {
    val playerActorRef: ActorRef = registeredPlayers(playerId)

    val response: ActionResponse =
      ActorCommunicationHelper.askWithTimeout[ActionRequest.type, ActionResponse](
        playerActorRef,
        ActionRequest,
        actionTimeout)

    response.action
  }

  override def logAction(playerId: PlayerId, action: Action): Unit = {
    sendToAllPlayers(ActionLog(playerId, action))

  }

  override def logGameStatus(gameState: GameState): Unit = {
    val gameStatus: GameStatus = GameStatus(gameState)

    sendToAllPlayers(GameStatusMessage(gameStatus))
  }

  override def logHandStatus(gameState: GameState): Unit = {
    val handStatus: HandStatus = HandStatus(gameState)

    sendToAllPlayers(HandStatusMessage(handStatus))
  }

  override def logShowdownStatus(gameState: GameState): Unit = {
    val showdownStatus: ShowdownStatus = ShowdownStatus(gameState)

    sendToAllPlayers(ShowdownStatusMessage(showdownStatus))
  }

  override def logEndingStatus(results: CompleteHandResults): Unit = {
    val endingStatus: EndingStatus = EndingStatus(results)

    sendToAllPlayers(EndingStatusMessage(endingStatus))
  }

  private def sendToAllPlayers[MessageType](message: MessageType): Unit = {
    for(actor <- registeredPlayers.values)
      actor ! message
  }
}
