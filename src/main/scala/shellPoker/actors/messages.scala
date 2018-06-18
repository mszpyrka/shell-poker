package shellPoker.actors

import shellPoker.gameEngine.gameplay.status.{EndingStatus, GameStatus, HandStatus, ShowdownStatus}
import shellPoker.gameEngine.player.PlayerId
import shellPoker.gameEngine.playerAction.{Action, ActionValidation}

// ===================================================================================================================
// Communication between RemoteCommunicator and User
// ===================================================================================================================

// RemoteCommunicator -> User
final case object ActionRequest
final case class ValidationMessage(validation: ActionValidation)
final case class ActionLog(playerId: PlayerId, action: Action)
final case class HandStatusMessage(handStatus: HandStatus)
final case class GameStatusMessage(gameStatus: GameStatus)
final case class ShowdownStatusMessage(showdownStatus: ShowdownStatus)
final case class EndingStatusMessage(endingStatus: EndingStatus)

// User -> RemoteCommunicator
final case class ActionResponse(action: Action)


// ===================================================================================================================
// Communication between ServerSystem and RoomSupervisor
// ===================================================================================================================

// ServerSystem -> RoomSupervisor
final case object StartGame


// ===================================================================================================================
// Communication between Client and Server
// ===================================================================================================================

// Client -> Server
final case class Register(playerId: PlayerId)
final case object RoomStatusRequest
final case class RoomStatus(xD: String)


// Server -> Client
final case object RegisterSuccess
final case object RegisterFail



