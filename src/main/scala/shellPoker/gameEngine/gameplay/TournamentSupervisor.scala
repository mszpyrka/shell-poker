package shellPoker.gameEngine.gameplay
import shellPoker.gameEngine.table._



class TournamentSupervisor[T < HandSupervisor](gameSettings: GameSettings) extends RoomSupervisor(gameSettings) {

    /** Adds player to the pendingPlayers list. */
    override def addPendingPlayer(pendingPlayer: PlayerId): Unit = {
        //If there is no such seat throw some kind of exception
        if(pendingPlayer.seatNumber >= gameSettings.seatsNumber)
            ???

        //Counting players that are sitting/have reserved same seat as pendingPlayer
        val sameSeatCount: Int = 
            currentPlayers.count(_.seatNumber == pendingPlayer.seatNumber) + 
            pendingPlayers.count(_.seatNumber == pendingPlayer.seatNumber)

        //If there are none, add pendingPlayer to pendingPlayers
        if(sameSeatCount == 0) pendingPlayers = pendingPlayer :: pendingPlayers

        //else throw some kind of exception
        else 
            ???
    }

    /** Gets updated gameState including pending players, excluding no chip players. */
    override def getUpdatedGameState(previousEndingGameState: GameState): GameState = {

        //Get previous table seats
        val previousEndingSeats: List[TableSeat] = previousEndingGameState.table.seats

        //Remove no chip players
        previousEndingSeats.filter(!_.isEmpty).foreach((tableSeat: TableSeat) => if (tableSeat.player.chipStack.chipCount <= 0) tableSeat.removePlayer() )

        //Add pending players
        for(pendingPlayer <- pendingPlayers) 
          previousEndingSeats(pendingPlayer.seatNumber).createAndAddPlayer(pendingPlayer.name, gameSettings.startingStack)

        //Update player buffers
        currentPlayers = Nil
        pendingPlayers = Nil

        //Make new current players list
        previousEndingSeats.filter(!_.isEmpty).foreach((tableSeat: TableSeat) => 
          currentPlayers = PlayerId(tableSeat.player.name, tableSeat.seatNumber) :: currentPlayers)

        //Return new updated game state
        previousEndingGameState.getModified(handNumber = previousEndingGameState.handNumber + 1)
    }

    /** Gets initial state of the game, including initialPlayers list. */
    override def getInitialGameState(initialPlayers: List[PlayerId]): GameState = {
        //Get empty table state according to gameSettings
        val emptyTableState: GameState = GameState.getEmptyTableState(gameSettings)

        //Add initial players to the table
        for(initialPlayer <- initialPlayers)
            emptyTableState.table.seats(initialPlayer.seatNumber).createAndAddPlayer(initialPlayer.name, gameSettings.startingStack)

        //Update player buffers
        currentPlayers = initialPlayers
        pendingPlayers = Nil

        //Return ready for game initial state
        emptyTableState
    }

    /** Gets new supervisor for this room supervisor */
    override def getNewSupervisor(gameState: GameState): HandSupervisor = {
        new LocalTestSupervisor(gameState)
    }

}