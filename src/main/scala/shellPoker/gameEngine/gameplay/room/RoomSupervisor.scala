package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand.{HandSupervisor, Communicator}
import shellPoker.gameEngine.table._
import shellPoker.gameEngine.gameplay._
import shellPoker.gameEngine.player.{ChipStack, Player, PlayerId}


/* Main game supervisor, responsible for running the game. */
abstract class RoomSupervisor(gameSettings: GameSettings, communicator: Communicator) {

  // ===================================================================================================================
  // Class API:
  // ===================================================================================================================

  /** Runs main loop of the game. */
  def run(initialPlayerIds: List[PlayerId]): Unit = {

    val initialGameState: GameState = getInitialGameState(initialPlayerIds)
    var handSupervisor: HandSupervisor = new HandSupervisor(initialGameState, communicator)

    // Run the game
    while(true) {

      val handEndingState: GameState = handSupervisor.playSingleHand()
      val pendingPlayers: List[PlayerId] = getPendingPlayers
      val updatedGameState = getUpdatedGameState(handEndingState, pendingPlayers)

      handSupervisor = new HandSupervisor(updatedGameState, communicator)
    }

  }



  // ===================================================================================================================
  // Abstract methods:
  // ===================================================================================================================

  /** Obtains players from external source that joined the game during some hand. */
  protected def getPendingPlayers: List[PlayerId]



  // ===================================================================================================================
  // Private helper methods:
  // ===================================================================================================================

  /** Gets initial state of the game, adds initialPlayers list. */
  private def getInitialGameState(initialPlayerIds: List[PlayerId]): GameState = {

    val emptyTableState: GameState = GameState.getEmptyTableState(gameSettings)
    val table: PokerTable = emptyTableState.table
    addPlayers(table, initialPlayerIds)

    table.positionManager.pickRandomPositions()
    table.resetCards()

    emptyTableState
  }


  /** Gets updated gameState - adds pending players, removes empty stack players. */
  private def getUpdatedGameState(previousGameState: GameState, pendingPlayerIds: List[PlayerId]): GameState = {

    val table: PokerTable = previousGameState.table

    removeBustedPlayers(table)
    addPlayers(table, pendingPlayerIds)
    table.positionManager.movePositions()
    table.resetCards()
    table.players.foreach(_.setActive())
    table.potManager.clear()

    //Return new updated game state
    previousGameState.getModified(
      handNumber = previousGameState.handNumber + 1,
      currentBettingRound = 0)
  }


  /** Removes busted players from the poker table. */
  private def removeBustedPlayers(table: PokerTable): Unit =
    table.seats.filter(!_.isEmpty).foreach((tableSeat: TableSeat) =>
      if (tableSeat.player.chipStack.chipCount <= 0) 
        tableSeat.removePlayer())


  /** Adds list of playerIds to the poker table. */
  private def addPlayers(table: PokerTable, playerIds: List[PlayerId]): Unit = {

    def addPlayerById(id: PlayerId): Unit = {

      val newPlayer = new Player(
        id.name,
        table.seats(id.seatNumber),
        new ChipStack(gameSettings.startingStack))

      table.seats(id.seatNumber).addPlayer(newPlayer)
    }

    playerIds.foreach((id: PlayerId) => addPlayerById(id))
  }
}