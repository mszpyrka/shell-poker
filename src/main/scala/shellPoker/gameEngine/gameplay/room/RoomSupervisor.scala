package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand.HandSupervisor
import shellPoker.gameEngine.table._
import shellPoker.gameEngine.gameplay._


/* Responsible for holding basic player information */
case class PlayerId(name: String, seatNumber: Int)


/* Main game supervisor, responsible for running the game. */
abstract class RoomSupervisor(gameSettings: GameSettings) {

  // ===================================================================================================================
  // Abstract API:
  // ===================================================================================================================

  /** Gets players that joined the game during some hand. */
  protected def getPendingPlayers(): List[PlayerId]

  /** Gets chosen hand supervisor for this room supervisor. */
  protected def getHandSupervisor(gameState: GameState): HandSupervisor 


  // ===================================================================================================================
  // Concrete API:
  // ===================================================================================================================

  /** Gets initial state of the game, adds initialPlayers list. */
  private def getInitialGameState(initialPlayerIds: List[PlayerId]): GameState = {

    //Get empty table state according to gameSettings
    val emptyTableState: GameState = GameState.getEmptyTableState(gameSettings)

    //Get the poker table from gameState
    val table: PokerTable = emptyTableState.table

    //Add initial players to the table
    addPlayers(table, initialPlayerIds)

    //Picks positions 
    table.positionManager.pickRandomPositions()

    //Return ready for game initial state
    emptyTableState
  }


  /** Gets updated gameState - adds pending players, removes empty stack players. */
  private def getUpdatedGameState(previousGameState: GameState, pendingPlayerIds: List[PlayerId]): GameState = {

    //Get previous table seats
    val table: PokerTable = previousGameState.table

    //Remove busted players
    removeBustedPlayers(table)

    //Add pending players
    addPlayers(table, pendingPlayerIds)

    table.positionManager.movePositions()

    //Return new updated game state
    previousGameState.getModified(
      handNumber = previousGameState.handNumber + 1,
      currentBettingRound = 0
      )
  }


  /** Removes busted players from the poker table. */
  private def removeBustedPlayers(table: PokerTable) =
    table.seats.filter(!_.isEmpty).foreach((tableSeat: TableSeat) =>
      if (tableSeat.player.chipStack.chipCount <= 0) 
        tableSeat.removePlayer())


  /** Adds list of playerIds to the poker table. */
  private def addPlayers(table: PokerTable, playerIds: List[PlayerId]) = 
    playerIds.foreach((playerId: PlayerId) =>
      table.seats(playerId.seatNumber).createAndAddPlayer(playerId.name, gameSettings.startingStack))


  /** Runs main loop of the game. */
  def run(initialPlayerIds: List[PlayerId]): Unit = {

    //Get initial game state
    val initialGameState: GameState = getInitialGameState(initialPlayerIds)

    //Get initial hand supervisor
    var handSupervisor: HandSupervisor = getHandSupervisor(initialGameState)

    //Run the game
    while(true) {
      //Run a single hand, and return ending GameState
      val handEndingState: GameState = handSupervisor.playSingleHand()

      //Get pending players from external source
      val pendingPlayers: List[PlayerId] = getPendingPlayers()

      //Get an updated game state, adding pending players and removing no chip players
      val updatedGameState = getUpdatedGameState(handEndingState, pendingPlayers)

      //Create new hand supervisor
      handSupervisor = getHandSupervisor(updatedGameState)
    }

  }

}